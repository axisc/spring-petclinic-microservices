package org.springframework.samples.petclinic.communication.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.samples.petclinic.communication.email.EmailService;
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerCreatedQueueListener {
	
	private static final String QUEUE_NAME = "CreatedOwner";
	
	@Autowired
	private EmailService emailService;
	

	@JmsListener(destination = QUEUE_NAME)
	public void receiveMessage(Owner owner) {
		
		emailService.sendOwnerWelcomeEmail(owner);
		
	}

}