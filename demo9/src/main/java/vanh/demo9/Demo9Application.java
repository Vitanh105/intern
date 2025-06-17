package vanh.demo9;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import vanh.demo9.model.Gift;
import vanh.demo9.repository.GiftRepository;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class Demo9Application {
    @Autowired
    private GiftRepository giftRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Demo9Application.class, args);
    }

    @PostConstruct
    public void init() {
        String inventoryKey = "gift_inventory:1";
        redisTemplate.opsForValue().set(inventoryKey, "10");

        if (!giftRepository.existsById("1")) {
            Gift gift = new Gift();
            gift.setGiftId("1");
            gift.setGiftName("Quà đặc biệt");
            gift.setQuantity(10);
            giftRepository.save(gift);
        }
    }
}
