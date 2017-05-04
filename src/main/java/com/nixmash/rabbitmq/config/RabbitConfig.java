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

    public final static String exchangeName = "nixmashmq.exchange";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public List<Queue> qs() {
        return Arrays.asList(
                new Queue(ReservationQueue.MsgDisplay),
                new Queue(ReservationQueue.Display),
                new Queue(ReservationQueue.Create),
                new Queue(ReservationQueue.Show),
                new Queue(ReservationQueue.CreateAndShow)
        );
    }

/*
    @Bean
    public RabbitTemplate jsonRabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
*/

}