package vanh.demo15;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import vanh.demo15.model.Gift;
import vanh.demo15.repository.GiftRepository;

@SpringBootApplication
@EnableScheduling
public class Demo15Application {
    @Autowired
    private GiftRepository giftRepository;

    public static void main(String[] args) {
        SpringApplication.run(Demo15Application.class, args);
    }

    @PostConstruct
    public void init() {
        if (!giftRepository.existsById("1")) {
            Gift gift = new Gift();
            gift.setGiftId("1");
            gift.setGiftName("Quà đặc biệt");
            gift.setQuantity(10);
            giftRepository.save(gift);
        }
    }
}