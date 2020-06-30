package org.springframework.samples.petclinic.communication.listeners;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.samples.petclinic.communication.email.EmailService;
import org.springframework.stereotype.Component;

@Component
public class OwnerCreatedQueueListener {
	
	private static final String QUEUE_NAME = "CreatedOwner";
	
	@Autowired
	private EmailService emailService;
	

	@JmsListener(destination = QUEUE_NAME, containerFactory = "jmsListenerContainerFactory")
	public void receiveMessage(String ownerString) throws IOException {
		
		// remove the header information
		String refinedOwnerString = ownerString.substring(ownerString.indexOf("{"));
		
		emailService.sendOwnerWelcomeEmail(refinedOwnerString);
		
	}

}
