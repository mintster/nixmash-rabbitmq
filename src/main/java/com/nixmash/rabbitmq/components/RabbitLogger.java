package com.nixmash.rabbitmq.components;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitLogger {

    private final static String exchange = "nixmashmq.log.exchange";

    @RabbitListener(bindings = @QueueBinding(value = @Queue,
            exchange = @Exchange(value = exchange, type = ExchangeTypes.TOPIC),
            key = "com.nixmash.rabbitmq.io.msgs.MsgReceiver.INFO"
    ))
    public void handleLoggedStringMessage(String message) {
        System.out.println("STRING QUEUE MESSAGE: " + message);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue,
            exchange = @Exchange(value = exchange, type = ExchangeTypes.TOPIC),
            key = "com.nixmash.rabbitmq.io.data.DataReceiver.INFO"
    ))
    public void handleLoggedDataMessage(String message) {
        System.out.println("DATA QUEUE MESSAGE: " + message);
    }

}