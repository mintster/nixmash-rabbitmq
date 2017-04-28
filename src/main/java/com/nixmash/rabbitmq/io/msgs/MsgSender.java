package com.nixmash.rabbitmq.io.msgs;

import com.nixmash.rabbitmq.enums.ApplicationQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by daveburke on 4/20/17.
 */
@Component
public class MsgSender {

    private final RabbitTemplate rabbitTemplate;
    private final MsgReceiver msgReceiver;

    private static final Logger logger = LoggerFactory.getLogger(MsgSender.class);
    

    public MsgSender(MsgReceiver msgReceiver, RabbitTemplate rabbitTemplate ) {
        this.msgReceiver = msgReceiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendStringMessage()  {
//        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(ApplicationQueue.MessageDisplay, "Sending Simple Message from RabbitMQ!");
        try {
            boolean done =  msgReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
            if (!done) {
                logger.error("MESSAGE THREAD NOT COMPLETE AND TIMEOUT OCCURRED");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
