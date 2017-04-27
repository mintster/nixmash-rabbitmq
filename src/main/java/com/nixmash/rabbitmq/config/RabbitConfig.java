package com.nixmash.rabbitmq.config;

import com.nixmash.rabbitmq.enums.ApplicationQueue;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by daveburke on 4/20/17.
 */
@Configuration
public class RabbitConfig {

    public final static String msgQueue = "message-queue";
    public final static String exchangeName = "nixmashmq.exchange";

//    @Autowired
//    public SimpMessageSendingOperations messagingTemplate;

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public List<Queue> qs() {
        return Arrays.asList(
                new Queue(msgQueue),
                new Queue(ApplicationQueue.ReservationDisplay),
                new Queue(ApplicationQueue.ReservationCreate),
                new Queue(ApplicationQueue.ReservationShow)
        );
    }

}