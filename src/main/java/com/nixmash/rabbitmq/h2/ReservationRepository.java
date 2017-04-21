package com.nixmash.rabbitmq.h2;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by daveburke on 4/20/17.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAll();
    Reservation findOne(Long id);
}
