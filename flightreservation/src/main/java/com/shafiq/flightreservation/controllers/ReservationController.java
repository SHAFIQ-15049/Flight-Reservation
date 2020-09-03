package com.shafiq.flightreservation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shafiq.flightreservation.dto.ReservationRequest;
import com.shafiq.flightreservation.entities.Flight;
import com.shafiq.flightreservation.entities.Reservation;
import com.shafiq.flightreservation.repos.FlightRepository;
import com.shafiq.flightreservation.services.ReservationServices;

@Controller
public class ReservationController {
	
	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	ReservationServices reservationService;

	@RequestMapping("/showCompleteReservation")
	public String showCompleteReservation(@RequestParam("flightId") Long flightId,ModelMap modelMap)
	{
		Flight flight = flightRepository.findById(flightId).orElse(null);
		modelMap.addAttribute("flight", flight);
		return "completeReservation";
	}
	
	@RequestMapping(value="/completeReservation",method=RequestMethod.POST)
	public String completeReservation(ReservationRequest reservationRequest,ModelMap modelMap)
	{
		Reservation reservation = reservationService.bookFlight(reservationRequest);
		
		modelMap.addAttribute("msg", "Reservation created successfully and the id is "+reservation.getId());
		
		
		return "reservationConfirmation";
	}
	
}
