package com.shafiq.flightreservation.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shafiq.flightreservation.controllers.FlightController;
import com.shafiq.flightreservation.dto.ReservationRequest;
import com.shafiq.flightreservation.entities.Flight;
import com.shafiq.flightreservation.entities.Passenger;
import com.shafiq.flightreservation.entities.Reservation;
import com.shafiq.flightreservation.repos.FlightRepository;
import com.shafiq.flightreservation.repos.PassengerRepository;
import com.shafiq.flightreservation.repos.ReservationRepository;
import com.shafiq.flightreservation.util.EmailUtil;
import com.shafiq.flightreservation.util.PDFGenerator;

@Service
public class ReservationServiceImpl implements ReservationServices {

	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	PassengerRepository passengerRepository;
	
	@Autowired
	ReservationRepository reservationRepository;	
	
	@Autowired
	PDFGenerator pdfGenerator;
	
	@Autowired
	EmailUtil emailUtil;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);
	
	@Override
	public Reservation bookFlight(ReservationRequest request) {

		LOGGER.info("Inside bookFlight()");
		
		Long flightId = request.getFlightId();
		LOGGER.info("Fetching the flight with id : "+flightId);
		
		Flight flight = flightRepository.findById(flightId).orElse(null);
		
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getPassengerFirstName());
		passenger.setLastName(request.getPassengerLastName()); 
		passenger.setEmail(request.getPassengerEmail());
		passenger.setPhone(request.getPassengerPhone());
		
		LOGGER.info("Saving the passenger : "+passenger);
		Passenger savedPassenger = passengerRepository.save(passenger);
		
		Reservation reservation = new Reservation();
		reservation.setFlight(flight);
		reservation.setPassenger(savedPassenger);
		reservation.setCheckedIn(false);
		
		LOGGER.info("Saving the reservation : "+reservation);
		Reservation savedReservation = reservationRepository.save(reservation);
		
		String filePath = "C:\\Users\\shafi\\Documents\\reservation" +savedReservation.getId()+".pdf";
		
		LOGGER.info("Generating an itinerary");
		pdfGenerator.generateItinerary(savedReservation, filePath);
		
		LOGGER.info("Sending an email");
		emailUtil.sendItinerary(passenger.getEmail(), filePath);
		
		return savedReservation;
	}

}
