package com.nixmash.rabbitmq;

import com.nixmash.rabbitmq.config.RabbitConfig;
import com.nixmash.rabbitmq.h2.Reservation;
import com.nixmash.rabbitmq.h2.ReservationService;
import com.nixmash.rabbitmq.io.data.DataReceiver;
import com.nixmash.rabbitmq.io.msgs.MsgReceiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * Created by daveburke on 4/21/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
public class RabbitTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private MsgReceiver msgReceiver;

    @Autowired
    private DataReceiver dataReceiver;

    @Test
    public void messageTest() throws Exception {
        rabbitTemplate.convertAndSend(RabbitConfig.msgQueue, "Hello from RabbitMQ!");
        msgReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void dataTest() throws Exception {
        Reservation reservation = new Reservation("TestDude");
        rabbitTemplate.convertAndSend(RabbitConfig.dataQueue , reservation);
        dataReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
