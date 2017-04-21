package com.nixmash.rabbitmq.io.data;

import com.nixmash.rabbitmq.enums.ApplicationQueue;
import com.nixmash.rabbitmq.h2.Reservation;
import com.nixmash.rabbitmq.h2.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by daveburke on 4/20/17.
 */
@Component
public class DataSender {

    private static final Logger logger = LoggerFactory.getLogger(DataSender.class);

    private final RabbitTemplate rabbitTemplate;
    private final DataReceiver dataReceiver;
    private final ReservationService reservationService;

    public DataSender(DataReceiver dataReceiver, RabbitTemplate rabbitTemplate, ReservationService reservationService) {
        this.dataReceiver = dataReceiver;
        this.rabbitTemplate = rabbitTemplate;
        this.reservationService = reservationService;
    }

    public void sendReservationToDisplay()  {
        Reservation reservation = reservationService.getReservation(1L);

        System.out.println("Sending Reservation object...");
        rabbitTemplate.convertAndSend(ApplicationQueue.ReservationDisplay,reservation);
        getReceipt(dataReceiver.getDisplayLatch(), "DISPLAY");

        System.out.println("Sending Reservation object for Uppercase Display...");
        rabbitTemplate.convertAndSend(ApplicationQueue.ReservationCreate, reservation);
        getReceipt(dataReceiver.getCreateLatch(), "UPPER DISPLAY");
    }

    private void getReceipt(CountDownLatch latch, String threadName) {
        try {
            boolean done =  latch.await(10000, TimeUnit.MILLISECONDS);
            if (!done) {
                logger.error(String.format("%s THREAD NOT COMPLETE AND TIMEOUT OCCURRED", threadName));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
