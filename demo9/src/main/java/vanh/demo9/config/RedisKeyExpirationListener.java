package vanh.demo9.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer, RedisTemplate<String, String> redisTemplate) {
        super(listenerContainer);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        if (expiredKey.startsWith("otp:")) {
            String otp = expiredKey.replace("otp:", "");
            log.warn("OTP {} expired without confirmation", otp);
        }
        else if (expiredKey.startsWith("hold:")) {
            String giftId = redisTemplate.opsForValue().get(expiredKey);
            if (giftId != null) {
                String inventoryKey = "gift_inventory:" + giftId;
                redisTemplate.opsForValue().increment(inventoryKey, 1L);
                log.info("Released inventory for giftId: {} (Hold key expired: {})", giftId, expiredKey);
            }
        }
    }
}