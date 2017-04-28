package com.nixmash.rabbitmq.components;

import com.nixmash.rabbitmq.io.data.DataSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by daveburke on 4/20/17.
 */
@Component
public class RabbitUI {

    private static final Logger logger = LoggerFactory.getLogger(RabbitUI.class);

    private final DataSender dataSender;

    public RabbitUI(DataSender dataSender) {
        this.dataSender = dataSender;
    }

    public void init() {
        System.out.println();
        dataSender.createReservation();
    }
}
