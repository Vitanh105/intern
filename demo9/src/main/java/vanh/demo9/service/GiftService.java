package vanh.demo9.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vanh.demo9.dto.GiftClaimRequest;
import vanh.demo9.model.Gift;
import vanh.demo9.model.ScheduledGiftEvent;
import vanh.demo9.repository.GiftRepository;
import vanh.demo9.repository.ScheduledGiftEventRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;


@Slf4j
@Service
@RequiredArgsConstructor
public class GiftService {
    private final ScheduledGiftEventRepository scheduledEventRepository;
    private static final String HOLD_PREFIX = "hold:";
    private static final String OTP_PREFIX = "otp:";
    private final GiftRepository giftRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String INVENTORY_KEY_PREFIX = "gift_inventory:";
    private static final String LUA_SCRIPT =
            "local key = KEYS[1]\n" +
                    "local current = tonumber(redis.call('GET', key) or 0)\n" +
                    "if current > 0 then\n" +
                    "    redis.call('DECR', key)\n" +
                    "    return 1\n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";

    public String processRequest(GiftClaimRequest giftClaimRequest) {
        long startTime = System.currentTimeMillis();
        try {
            String inventoryKey = INVENTORY_KEY_PREFIX + giftClaimRequest.getGiftId();
            DefaultRedisScript<Long> script = new DefaultRedisScript<>(LUA_SCRIPT, Long.class);
            Long result = redisTemplate.execute(script, Collections.singletonList(inventoryKey));

            if (result == 1) {
                String otp = generateOTP();
                String holdKey = HOLD_PREFIX + giftClaimRequest.getRequestId();

                redisTemplate.opsForValue().set(holdKey, giftClaimRequest.getGiftId(), Duration.ofMinutes(1));
                redisTemplate.opsForValue().set(OTP_PREFIX + otp, giftClaimRequest.getRequestId(), Duration.ofMinutes(1));
                log.info("{} - SUCCESS - OTP generated: {} - {} ms", giftClaimRequest.getRequestId(), otp, System.currentTimeMillis() - startTime);
                return otp;
            } else {
                log.warn("{} - REJECT - {} ms", giftClaimRequest, System.currentTimeMillis() - startTime);
                return null;
            }
        } catch (Exception e) {
            log.error("Error processing request {}: {}", giftClaimRequest, e.getMessage());
            return null;
        }
    }

    @Transactional
    public boolean confirmGift(String requestId, String otp) {
        String storedRequestId = redisTemplate.opsForValue().get(OTP_PREFIX + otp);
        if (storedRequestId == null || !storedRequestId.equals(requestId)) {
            log.warn("Invalid OTP confirmation attempt - RequestID: {}, OTP: {}", requestId, otp);
            return false;
        }
        String holdKey = HOLD_PREFIX + requestId;
        String giftId = redisTemplate.opsForValue().get(holdKey);
        if (giftId == null) {
            log.warn("Hold key not found for RequestID: {}", requestId);
            return false;
        }

        Gift gift = giftRepository.findByIdWithLock(giftId);
        if (gift != null && gift.getQuantity() > 0) {
            gift.setQuantity(gift.getQuantity() - 1);
            giftRepository.save(gift);
            log.info("Gift confirmed - RequestID: {}, OTP: {}, GiftID: {}", requestId, otp, giftId);
        }

        redisTemplate.delete(holdKey);
        redisTemplate.delete(OTP_PREFIX + otp);
        log.info("OTP {} confirmed successfully for RequestID: {}", otp, requestId);
        return true;
    }

    private String generateOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void updateDatabaseAsync(GiftClaimRequest giftClaimRequest) {
        log.debug("Bắt đầu cập nhật database cho giftId: {}", giftClaimRequest.getGiftId());
        try {
            Gift gift = giftRepository.findByIdWithLock(giftClaimRequest.getGiftId());
            if (gift != null && gift.getQuantity() > 0) {
                gift.setQuantity(gift.getQuantity() - 1);
                giftRepository.save(gift);
                log.info("Cập nhật thành công giftId: {}", giftClaimRequest.getGiftId());
            } else {
                log.warn("Không tìm thấy quà hoặc số lượng đã hết cho giftId: {}", giftClaimRequest.getGiftId());
            }
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật database cho giftId {}: {}", giftClaimRequest.getGiftId(), e.getMessage());
            throw e;
        }
    }

    public void checkScheduledEvent(String giftId) {
        Optional<ScheduledGiftEvent> eventOpt = scheduledEventRepository.findByGiftId(giftId);
        if (eventOpt.isPresent()) {
            ScheduledGiftEvent event = eventOpt.get();
            if (LocalDateTime.now().isBefore(event.getStartTime())) {
                throw new RuntimeException("Event chưa bắt đầu cho giftId: " + giftId);
            }
            event.setActive(true);
            scheduledEventRepository.save(event);
        }
    }
}