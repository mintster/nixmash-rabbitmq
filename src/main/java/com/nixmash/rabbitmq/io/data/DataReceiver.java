package com.nixmash.rabbitmq.io.data;

import com.nixmash.rabbitmq.enums.ApplicationQueue;
import com.nixmash.rabbitmq.h2.Reservation;
import com.nixmash.rabbitmq.h2.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void createReservation(Reservation reservation) {
        System.out.println("Received Reservation <" + reservation.getReservationName() + ">");
        logger.info("Reservation Received to Upper: " + reservation.getReservationName().toUpperCase());
        createLatch.countDown();
    }

    public CountDownLatch getDisplayLatch() {
        return displayLatch;
    }
    public CountDownLatch getCreateLatch() {
        return createLatch;
    }

}
