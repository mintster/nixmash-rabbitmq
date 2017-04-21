package com.nixmash.rabbitmq;

import com.nixmash.rabbitmq.h2.Reservation;
import com.nixmash.rabbitmq.h2.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by daveburke on 4/20/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class H2Tests {

    @Autowired
    ReservationService reservationService;

    @Test
    public void getReservations() throws Exception {
        List<Reservation> reservations = reservationService.getReservations();
        reservations.forEach(r -> System.out.println(r.toString()));
    }
}
