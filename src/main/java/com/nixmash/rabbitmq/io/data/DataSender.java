package com.nixmash.rabbitmq.io.data;

import com.nixmash.rabbitmq.enums.ApplicationQueue;
import com.nixmash.rabbitmq.h2.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by daveburke on 4/20/17.
 */
@Component
public class DataSender {

    private static final Logger logger = LoggerFactory.getLogger(DataSender.class);

    private final RabbitTemplate rabbitTemplate;

    public DataSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void createReservation() {

        Reservation reservation = new Reservation();

        // Using @SendTo

        rabbitTemplate.convertAndSend(ApplicationQueue.ReservationCreate, new Reservation("Waldo"));
        reservation = (Reservation) rabbitTemplate.receiveAndConvert(ApplicationQueue.ReservationShow, 10_000);
        logger.info("Reservation Created: " + reservation.toString());

        // Sending and Receiving from a Single Queue

        reservation = (Reservation) rabbitTemplate.convertSendAndReceive(ApplicationQueue.ReservationCreateAndShow, new Reservation("Pete"));
        logger.info("Reservation Created: " + reservation.toString());
    }

}
