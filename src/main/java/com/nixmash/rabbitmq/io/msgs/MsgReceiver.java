package com.nixmash.rabbitmq.io.msgs;

import com.nixmash.rabbitmq.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@RabbitListener(queues= RabbitConfig.msgQueue)
public class MsgReceiver {

    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
