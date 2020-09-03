package com.shafiq.flightreservation.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shafiq.flightreservation.entities.User;
import com.shafiq.flightreservation.repos.UserRepository;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping("/showReg")
	public String userRegistrationPage()
	{
		LOGGER.info("inside userRegistrationPage()");
		return "login/registerUser";
	}
	
	@RequestMapping(value="/registerUser",method=RequestMethod.POST)
	public String register(@ModelAttribute("user") User user)
	{
		LOGGER.info("inside register()"+user);
		userRepository.save(user);
		return "login/login";
	}
	
	@RequestMapping("/showLogin")
	public String userLogin()
	{
		LOGGER.info("inside userLogin()");
		return "login/login";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam("email") String email,@RequestParam("password")String password,ModelMap modelMap)
	{
		LOGGER.info("inside Login() and the email is : "+email);
		LOGGER.error("ERROR");
		LOGGER.warn("WARN");
		LOGGER.info("INFO");
		LOGGER.debug("DEBUG");
		LOGGER.trace("TRACE");
		
		
		
		User user = userRepository.findByEmail(email);
		if(user.getPassword().equals(password))
		{
			return "findFlights";
		}
		else {
			modelMap.addAttribute("msg", "Invalid user name or password. Please try again.");
		}
		return "login/login";
	}
	
}
