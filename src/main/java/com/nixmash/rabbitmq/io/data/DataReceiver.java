package com.nixmash.rabbitmq.io.data;

import com.nixmash.rabbitmq.enums.ApplicationQueue;
import com.nixmash.rabbitmq.h2.Reservation;
import com.nixmash.rabbitmq.h2.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by daveburke on 4/20/17.
 */
@Component
public class DataReceiver {

    private static final Logger logger = LoggerFactory.getLogger(DataReceiver.class);

    private CountDownLatch displayLatch = new CountDownLatch(1);
    private CountDownLatch createLatch = new CountDownLatch(1);

    private final ReservationService reservationService;

    @Autowired
    public DataReceiver(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

@RabbitListener(queues = ApplicationQueue.ReservationDisplay)
public void displayReservation(Reservation reservation) {
        System.out.println("Received Reservation <" + reservation.getReservationName() + ">");
        logger.info("Reservation Received: " + reservation.getReservationName());
        displayLatch.countDown();
    }

    @RabbitListener(queues = ApplicationQueue.ReservationCreate)
    @SendTo(ApplicationQueue.ReservationShow)
    public Message<Reservation> createReservation(Reservation reservation) {
        reservation = new Reservation("Tommy");
        System.out.println("Creating Reservation <" + reservation.getReservationName() + ">");
        logger.info("New Reservation Name: " + reservation.getReservationName().toUpperCase());
//        return reservation;
        createLatch.countDown();
        return MessageBuilder
                .withPayload(reservation)
                .setHeader("code", 12345)
                .build();
    }

    @RabbitListener(queues = ApplicationQueue.ReservationShow)
    public Reservation showReservation(Message message) {
        return (Reservation) message.getPayload();
    }

    public CountDownLatch getDisplayLatch() {

        return displayLatch;
    }

    public CountDownLatch getCreateLatch() {
        return createLatch;
    }

}
