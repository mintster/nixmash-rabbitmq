package com.nixmash.rabbitmq.io.data;

import com.nixmash.rabbitmq.enums.ReservationQueue;
import com.nixmash.rabbitmq.h2.Reservation;
import com.nixmash.rabbitmq.h2.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by daveburke on 4/20/17.
 */
@Component
public class DataReceiver {

    private static final Logger logger = LoggerFactory.getLogger(DataReceiver.class);

    private CountDownLatch createLatch = new CountDownLatch(1);
    private CountDownLatch jsonCreateLatch = new CountDownLatch(1);
    private CountDownLatch createAndShowLatch = new CountDownLatch(1);

    private final ReservationService reservationService;

    @Autowired
    public DataReceiver(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @RabbitListener(queues = ReservationQueue.Create)
    @SendTo(ReservationQueue.Show)
    public Reservation createReservation(Reservation reservation) {
        logger.info("Reservation Received: " + reservation.toString());
        Reservation saved = reservationService.createReservation(reservation);
        createLatch.countDown();
        return saved;
    }

    @RabbitListener(queues = ReservationQueue.Show)
    public Reservation showReservation(Reservation reservation) {
        return reservation;
    }

    @RabbitListener(queues = ReservationQueue.CreateAndShow)
    public Reservation createAndReturnReservation(Reservation reservation) {
        logger.info("Reservation Received: " + reservation.toString());
        createAndShowLatch.countDown();
        return reservationService.createReservation(reservation);
    }

/*
    @RabbitListener(queues = ReservationQueue.JsonCreate)
    public Reservation createJsonReservation(@Payload Reservation reservation) {
//        Reservation reservation = deserialize(message.getPayload());
        logger.info("Message Received: " + reservation.toString());
        jsonCreateLatch.countDown();
        return reservationService.createReservation(reservation);
    }
*/

    @RabbitListener(queues = ReservationQueue.JsonCreate)
    public Reservation createJsonReservation(Message message, @Header("VIP") boolean vip) {
        Reservation reservation = (Reservation) message.getPayload();
        logger.info("Message Received: " + message.toString());
        jsonCreateLatch.countDown();
        return reservationService.createReservation(reservation);
    }

    // region CountdownLatch

    public CountDownLatch getCreateAndShowLatch() {
        return createAndShowLatch;
    }
    public CountDownLatch getCreateLatch() {
        return createLatch;
    }
    public CountDownLatch getJsonCreateLatch() {
        return jsonCreateLatch;
    }

    // endregion

}
