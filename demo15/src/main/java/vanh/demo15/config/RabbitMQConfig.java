package vanh.demo15.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
    public static final String GIFT_CLAIM_QUEUE = "gift.claim.queue";
    public static final String GIFT_HOLD_EXCHANGE = "gift.hold.exchange";
    public static final String GIFT_HOLD_QUEUE = "gift.hold.queue";
    public static final String GIFT_HOLD_ROUTING_KEY = "gift.hold";
    public static final String GIFT_CONFIRM_QUEUE = "gift.confirm.queue";

    @Bean
    public Queue giftClaimQueue() {
        return new Queue(GIFT_CLAIM_QUEUE, true);
    }

    @Bean
    public Queue giftHoldQueue() {
        return QueueBuilder.durable(GIFT_HOLD_QUEUE)
                .deadLetterExchange("")
                .deadLetterRoutingKey(GIFT_CLAIM_QUEUE)
                .build();
    }

    @Bean
    public Queue giftConfirmQueue() {
        return new Queue(GIFT_CONFIRM_QUEUE, true);
    }

    @Bean
    public DirectExchange giftHoldExchange() {
        return new DirectExchange(GIFT_HOLD_EXCHANGE);
    }

    @Bean
    public Binding bindingHold(Queue giftHoldQueue, DirectExchange giftHoldExchange) {
        return BindingBuilder.bind(giftHoldQueue)
                .to(giftHoldExchange)
                .with(GIFT_HOLD_ROUTING_KEY);
    }

    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(
                "delayed.exchange",
                "x-delayed-message",
                true,
                false,
                args
        );
    }

    @Bean
    public Binding bindingDelayed(Queue giftHoldQueue, CustomExchange delayedExchange) {
        return BindingBuilder.bind(giftHoldQueue)
                .to(delayedExchange)
                .with(RabbitMQConfig.GIFT_HOLD_ROUTING_KEY)
                .noargs();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareQueue(giftClaimQueue());
        admin.declareQueue(giftHoldQueue());
        admin.declareQueue(giftConfirmQueue());
        admin.declareExchange(giftHoldExchange());
        admin.declareBinding(bindingHold(giftHoldQueue(), giftHoldExchange()));
        admin.declareExchange(delayedExchange());
        admin.declareBinding(bindingDelayed(giftHoldQueue(), delayedExchange()));
        return admin;
    }
}