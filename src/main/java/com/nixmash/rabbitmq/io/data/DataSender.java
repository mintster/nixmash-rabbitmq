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
    private final RabbitTemplate jsonRabbitTemplate;

    public DataSender(RabbitTemplate rabbitTemplate, RabbitTemplate jsonRabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.jsonRabbitTemplate = jsonRabbitTemplate;
    }

    public void createReservation() {

        jsonRabbitTemplate.convertAndSend(ApplicationQueue.ReservationCreate, new Reservation("Waldo"));
        Reservation reservation = (Reservation) rabbitTemplate.receiveAndConvert(ApplicationQueue.ReservationShow, 10_000);
        System.out.println("Reservation Created: " + reservation.toString());

        reservation = (Reservation) rabbitTemplate.convertSendAndReceive(ApplicationQueue.ReservationCreateAndShow, new Reservation("Pete"));
        System.out.println("Reservation Created: " + reservation.toString());
    }

}
