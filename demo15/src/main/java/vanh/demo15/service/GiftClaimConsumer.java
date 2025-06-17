package vanh.demo15.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vanh.demo15.config.RabbitMQConfig;
import vanh.demo15.dto.GiftClaimRequest;
import vanh.demo15.model.Gift;
import vanh.demo15.repository.GiftRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftClaimConsumer {
    private final GiftRepository giftRepository;

    @RabbitListener(queues = RabbitMQConfig.GIFT_CLAIM_QUEUE)
    @Transactional
    public void handleGiftClaim(@Payload GiftClaimRequest request) {
        log.info("Nhận request: {}", request.getRequestId());
        try {
            Gift gift = giftRepository.findByIdWithLock(request.getGiftId());
            if (gift != null && gift.getQuantity() > 0) {
                gift.setQuantity(gift.getQuantity() - 1);
                giftRepository.save(gift);
                log.info("Xử lý thành công request: {}", request.getRequestId());
            } else {
                log.warn("Hết quà hoặc không tìm thấy quà cho request: {}", request.getRequestId());
            }
        } catch (Exception e) {
            log.error("Lỗi xử lý request {}: {}", request.getRequestId(), e.getMessage());
        }
    }
}