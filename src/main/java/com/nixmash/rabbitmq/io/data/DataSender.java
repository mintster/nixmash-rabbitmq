package com.nixmash.rabbitmq.io.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixmash.rabbitmq.enums.ReservationQueue;
import com.nixmash.rabbitmq.h2.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

//import org.springframework.amqp.core.MessageBuilder;

/**
 * Created by daveburke on 4/20/17.
 */
@Component
public class DataSender {

    private static final Logger logger = LoggerFactory.getLogger(DataSender.class);

    private final RabbitTemplate rabbitTemplate;
    private final RabbitTemplate jsonRabbitTemplate;
    private final DataReceiver dataReceiver;

    public DataSender(RabbitTemplate rabbitTemplate, RabbitTemplate jsonRabbitTemplate, DataReceiver dataReceiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.jsonRabbitTemplate = jsonRabbitTemplate;
        this.dataReceiver = dataReceiver;
    }

    public void createReservation() {

        Reservation reservation = new Reservation();
        Reservation created = new Reservation();
        String createQueue = ReservationQueue.Create;
        String showQueue = ReservationQueue.Show;
        String jsonCreateQueue = ReservationQueue.JsonCreate;
        String createAndShowQueue = ReservationQueue.CreateAndShow;

        //  region Using @SendTo

        reservation = new Reservation("Waldo");
        rabbitTemplate.convertAndSend(createQueue, reservation);
        getReceipt(dataReceiver.getCreateLatch(), createQueue);
        created = (Reservation) rabbitTemplate.receiveAndConvert(showQueue, 10_000);
        logger.info("Reservation Created: " + created.toString() + "\n");

        // endregion

        // region Sending and Receiving from a Single Queue

        reservation = new Reservation("Pete");
        created = (Reservation)
                rabbitTemplate.convertSendAndReceive(createAndShowQueue, reservation);
        getReceipt(dataReceiver.getCreateAndShowLatch(), createAndShowQueue);
        logger.info("Reservation Created: " + created.toString() + "\n");

        // endregion

        // Sending Json WITHOUT Jackson2JsonMessageConverter in Rabbit.Config and with MessageBuilder

        String json = ReservationToJson(new Reservation("Harriet"));

        Message jsonMessage = MessageBuilder
                .withBody(json.getBytes())
                .andProperties(MessagePropertiesBuilder.newInstance().setContentType("application/json")
                        .build())
                .build();
        created = (Reservation)
                rabbitTemplate.convertSendAndReceive(jsonCreateQueue, jsonMessage);
        getReceipt(dataReceiver.getJsonCreateLatch(), jsonCreateQueue);
        logger.info("Reservation Created: " + created.toString() + "\n");

        // Sending Json WITHOUT Jackson2JsonMessageConverter in Rabbit.Config

        json = ReservationToJson(new Reservation("Janet"));
        created = (Reservation)
                rabbitTemplate.convertSendAndReceive(jsonCreateQueue, json);
        getReceipt(dataReceiver.getJsonCreateLatch(), jsonCreateQueue);
        logger.info("Reservation Created: " + created.toString() + "\n");

        // Sending Json WITH Jackson2JsonMessageConverter in Rabbit.Config

//                reservation = new Reservation("Jack");
//                created = (Reservation) rabbitTemplate.convertSendAndReceive(jsonCreateQueue, reservation);
//                getReceipt(dataReceiver.getJsonCreateLatch(), jsonCreateQueue);
//                logger.info("Reservation Created: " + created.toString());

    }

    private String ReservationToJson(Reservation reservation) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(reservation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    private void getReceipt(CountDownLatch latch, String threadName) {
        try {
            boolean done = latch.await(10000, TimeUnit.MILLISECONDS);
            if (!done) {
                logger.error(String.format("%s THREAD NOT COMPLETE -- TIMEOUT OCCURRED", threadName));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
