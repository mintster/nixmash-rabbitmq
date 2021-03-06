package com.nixmash.rabbitmq.config;

import com.nixmash.rabbitmq.enums.ReservationQueue;
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

    private final static String exchangeName = "nixmashmq.exchange";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public List<Queue> qs() {
        return Arrays.asList(
                new Queue("jsonQueue"),
                new Queue(ReservationQueue.MsgDisplay),
                new Queue(ReservationQueue.Display),
                new Queue(ReservationQueue.Create),
                new Queue(ReservationQueue.Show),
                new Queue(ReservationQueue.JsonCreate),
                new Queue(ReservationQueue.CreateAndShow)
        );
    }

//    @Bean
//    public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMessageConverter(jsonConverter());
//        return template;
//    }
//
//    @Bean
//    public MessageConverter jsonConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
}