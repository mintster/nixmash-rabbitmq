package com.nixmash.rabbitmq;

import com.nixmash.rabbitmq.enums.ReservationQueue;
import com.nixmash.rabbitmq.h2.Reservation;
import com.nixmash.rabbitmq.io.data.DataSender;
import com.nixmash.rabbitmq.io.msgs.MsgSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by daveburke on 4/21/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class RabbitTests {

    @Autowired
    private MsgSender msgSender;

    @Autowired
    private DataSender dataSender;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    public void createReservation_SingleQueue() throws Exception {

        String queue = ReservationQueue.CreateAndShow;
        String name = "Pete";

        Reservation reservation = new Reservation(name);
        assertNull(reservation.getId());

        reservation = (Reservation) rabbitTemplate.convertSendAndReceive(queue, reservation);
        assertNotNull(reservation.getId());
        assertEquals(reservation.getReservationName(), name);
    }

    @Test
    public void createReservation_SendTo() throws Exception {

        String queue = ReservationQueue.Create;
        String sendToQueue = ReservationQueue.Show;
        String name = "Waldo";

        Reservation reservation = new Reservation(name);
        assertNull(reservation.getId());

        rabbitTemplate.convertAndSend(queue, reservation);
        reservation = (Reservation) rabbitTemplate.receiveAndConvert(sendToQueue, 10_000);
        assertNotNull(reservation.getId());
        assertEquals(reservation.getReservationName(), name);
    }

}
