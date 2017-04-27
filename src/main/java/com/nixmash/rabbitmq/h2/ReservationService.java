package com.nixmash.rabbitmq.h2;

import java.util.List;

/**
 * Created by daveburke on 4/20/17.
 */
public interface ReservationService {
    public abstract List<Reservation> getReservations();

    Reservation getReservation(Long id);

    Reservation createReservation(Reservation reservation);
}
