package com.nixmash.rabbitmq.components;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LogReceiver {

    @RabbitListener(bindings = @QueueBinding(value = @Queue,
            exchange = @Exchange(value = "nixmash-mq-logs",
                    durable = "true", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = "nixmash-mq-queue"))
    public void logMessage(String message) {
        System.out.println("LOGGED: " + message);
    }

}
