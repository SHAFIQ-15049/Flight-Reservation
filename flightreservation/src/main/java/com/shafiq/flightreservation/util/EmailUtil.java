package com.shafiq.flightreservation.util;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

	@Autowired
	private JavaMailSender sender;
	
	public void sendItinerary(String toAddress,String filePath)
	{
		MimeMessage message = sender.createMimeMessage();
		
		try {
			MimeMessageHelper helper  = new MimeMessageHelper(message,true);
			helper.setTo(toAddress);
			helper.setSubject("Itinerary for your Flight");
			helper.setText("Please find your Itinerary attached.");
			helper.addAttachment("Itinerary", new File(filePath));
			sender.send(message);
			
		}catch(MessagingException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
