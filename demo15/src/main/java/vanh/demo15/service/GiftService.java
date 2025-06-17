package vanh.demo15.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vanh.demo15.config.RabbitMQConfig;
import vanh.demo15.dto.GiftClaimRequest;
import vanh.demo15.dto.GiftClaimResponse;
import vanh.demo15.model.Gift;
import vanh.demo15.model.GiftHold;
import vanh.demo15.model.ScheduledGiftEvent;
import vanh.demo15.repository.GiftHoldRepository;
import vanh.demo15.repository.GiftRepository;
import vanh.demo15.repository.ScheduledGiftEventRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository giftRepository;
    private final GiftHoldRepository giftHoldRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ScheduledGiftEventRepository scheduledEventRepository;

    @Value("${gift.hold.timeout:60000}")
    private long holdTimeout;

    @Transactional
    public GiftClaimResponse processRequest(GiftClaimRequest giftClaimRequest) {
        long startTime = System.currentTimeMillis();
        String requestId = giftClaimRequest.getRequestId();
        String giftId = giftClaimRequest.getGiftId();

        try {
            Optional<ScheduledGiftEvent> eventOpt = scheduledEventRepository.findByGiftId(giftId);
            if (eventOpt.isPresent()) {
                ScheduledGiftEvent event = eventOpt.get();
                if (!event.isActive()) {
                    if (LocalDateTime.now().isBefore(event.getStartTime())) {
                        log.warn("[{}] REJECT - Event not started yet for giftId: {}", requestId, giftId);
                        return new GiftClaimResponse(requestId, null, "EVENT_NOT_STARTED", startTime);
                    }
                    event.setActive(true);
                    scheduledEventRepository.save(event);
                    log.info("[{}] Event activated: {}", requestId, event.getEventId());
                }
            }

            Gift gift = giftRepository.findByIdWithLock(giftId);
            if (gift == null || gift.getQuantity() <= 0) {
                log.warn("[{}] REJECT - Out of stock for giftId: {}", requestId, giftId);
                return new GiftClaimResponse(requestId, null, "REJECT", startTime);
            }

            gift.setQuantity(gift.getQuantity() - 1);
            giftRepository.save(gift);
            log.info("[{}] SUCCESS - Claimed giftId: {}, Remaining: {}",
                    requestId, giftId, gift.getQuantity());

            String otp = generateOTP();
            GiftHold hold = new GiftHold();
            hold.setRequestId(requestId);
            hold.setGiftId(giftId);
            hold.setOtp(otp);
            hold.setExpireTime(LocalDateTime.now().plusSeconds(holdTimeout / 1000));
            giftHoldRepository.save(hold);

            rabbitTemplate.convertAndSend(
                    "delayed.exchange",
                    RabbitMQConfig.GIFT_HOLD_ROUTING_KEY,
                    requestId,
                    message -> {
                        message.getMessageProperties().setHeader("x-delay", holdTimeout);
                        return message;
                    }
            );

            return new GiftClaimResponse(
                    requestId,
                    otp,
                    "HOLD",
                    startTime
            );

        } catch (Exception e) {
            log.error("[{}] ERROR processing request: {}", requestId, e.getMessage());
            throw e;
        }
    }

    @Transactional
    public boolean confirmGift(String requestId, String otp) {
        GiftHold hold = giftHoldRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Hold not found"));

        if (!hold.getOtp().equals(otp)) {
            return false;
        }

        if (hold.isExpired()) {
            return false;
        }

        hold.setConfirmed(true);
        giftHoldRepository.save(hold);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.GIFT_CONFIRM_QUEUE,
                requestId
        );

        return true;
    }

    @RabbitListener(queues = RabbitMQConfig.GIFT_HOLD_QUEUE)
    @Transactional
    public void handleHoldTimeout(String requestId) {
        try {
            Optional<GiftHold> holdOpt = giftHoldRepository.findById(requestId);

            if (holdOpt.isEmpty()) {
                log.warn("[{}] SKIPPED - Hold not found, possibly already confirmed", requestId);
                return;
            }

            GiftHold hold = holdOpt.get();

            if (!hold.isConfirmed()) {
                Gift gift = giftRepository.findByIdWithLock(hold.getGiftId());
                if (gift != null) {
                    gift.setQuantity(gift.getQuantity() + 1);
                    giftRepository.save(gift);
                    log.info("[{}] RELEASED - Returned gift to stock. GiftId: {}, New quantity: {}",
                            requestId, hold.getGiftId(), gift.getQuantity());
                }
                log.warn("[{}] EXPIRED - Hold timeout", requestId);
            } else {
                log.info("[{}] SKIPPED - Hold already confirmed", requestId);
            }

            giftHoldRepository.delete(hold);

        } catch (Exception e) {
            log.error("[{}] ERROR processing hold timeout: {}", requestId, e.getMessage());
            throw e;
        }
    }

    @RabbitListener(queues = RabbitMQConfig.GIFT_CONFIRM_QUEUE)
    @Transactional
    public void handleConfirm(String requestId) {
        try {
            Optional<GiftHold> holdOpt = giftHoldRepository.findById(requestId);

            if (holdOpt.isEmpty()) {
                log.warn("[{}] SKIPPED - Hold not found for confirmation", requestId);
                return;
            }

            GiftHold hold = holdOpt.get();
            log.info("[{}] CONFIRMED - Gift successfully claimed", requestId);
            giftHoldRepository.delete(hold);

        } catch (Exception e) {
            log.error("[{}] ERROR confirming gift: {}", requestId, e.getMessage());
            throw e;
        }
    }

    private String generateOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}