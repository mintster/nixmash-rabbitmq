package com.nixmash.rabbitmq.io.data;

import com.nixmash.rabbitmq.enums.ApplicationQueue;
import com.nixmash.rabbitmq.h2.Reservation;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by daveburke on 4/20/17.
 */
@Component
public class DataReceiver {

    private CountDownLatch displayLatch = new CountDownLatch(1);
    private CountDownLatch createLatch = new CountDownLatch(1);

    @RabbitListener(queues = ApplicationQueue.ReservationDisplay)
    public void displayReservation(Reservation reservation) {
        System.out.println("Received Reservation <" + reservation.getReservationName() + ">");
        displayLatch.countDown();
    }

    @RabbitListener(queues = ApplicationQueue.ReservationCreate)
    public void createReservation(Reservation reservation) {
        System.out.println("UPPER RESERVATION <" + reservation.getReservationName().toUpperCase() + ">");
        createLatch.countDown();
    }

    public CountDownLatch getDisplayLatch() {
        return displayLatch;
    }
    public CountDownLatch getCreateLatch() {
        return createLatch;
    }

}
