package com.shafiq.flightreservation.services;

import com.shafiq.flightreservation.dto.ReservationRequest;
import com.shafiq.flightreservation.entities.Reservation;

public interface ReservationServices {

	public Reservation bookFlight(ReservationRequest request);
}
