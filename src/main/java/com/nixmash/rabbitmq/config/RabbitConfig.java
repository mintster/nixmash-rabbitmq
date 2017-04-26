package com.nixmash.rabbitmq.config;

import com.nixmash.rabbitmq.enums.ApplicationQueue;
import org.springframework.amqp.core.Queue;
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

    @Bean
    public List<Queue> qs() {
        return Arrays.asList(
                new Queue(msgQueue, false),
                new Queue(ApplicationQueue.ReservationDisplay, false),
                new Queue(ApplicationQueue.ReservationCreate, false)
        );
    }

}
