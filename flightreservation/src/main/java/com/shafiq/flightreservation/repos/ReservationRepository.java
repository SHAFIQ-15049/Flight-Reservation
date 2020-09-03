package com.shafiq.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shafiq.flightreservation.entities.Flight;
import com.shafiq.flightreservation.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
