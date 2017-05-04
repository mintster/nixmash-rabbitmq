package com.nixmash.rabbitmq.io.msgs;

import com.nixmash.rabbitmq.enums.ReservationQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@RabbitListener(queues = ReservationQueue.MsgDisplay)
public class MsgReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MsgReceiver.class);
    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        logger.info("Simple String received: " + message);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
