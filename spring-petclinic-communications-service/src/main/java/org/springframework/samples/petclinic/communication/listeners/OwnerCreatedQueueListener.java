package org.springframework.samples.petclinic.communication.listeners;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
	public void receiveMessage(String ownerString) throws IOException, InterruptedException {
		
		// remove the header information
		String refinedOwnerString = ownerString.substring(ownerString.indexOf("{"));
		TimeUnit.SECONDS.sleep(2);
		emailService.sendOwnerWelcomeEmail(refinedOwnerString);
		TimeUnit.SECONDS.sleep(15);
		
	}

}
