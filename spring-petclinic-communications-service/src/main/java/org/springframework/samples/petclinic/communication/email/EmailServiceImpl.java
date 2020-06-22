package org.springframework.samples.petclinic.communication.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.samples.petclinic.customers.model.Owner;

public class EmailServiceImpl implements EmailService {
    
	@Autowired
    private JavaMailSender emailSender;
	
	@Override
    public void sendOwnerWelcomeEmail(Owner owner) {
        SimpleMailMessage message = new SimpleMailMessage();
        // TODO add message body
        emailSender.send(message);
    }
}
