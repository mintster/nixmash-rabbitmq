package com.nixmash.rabbitmq;

import com.nixmash.rabbitmq.io.data.DataSender;
import com.nixmash.rabbitmq.io.msgs.MsgSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void messageTest() throws Exception {
        msgSender.sendStringMessage();
    }

    @Test
    public void dataTest() throws Exception {
        dataSender.sendReservationToDisplay();
    }
}
