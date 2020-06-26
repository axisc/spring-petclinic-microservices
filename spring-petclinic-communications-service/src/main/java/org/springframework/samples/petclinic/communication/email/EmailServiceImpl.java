package org.springframework.samples.petclinic.communication.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

public class EmailServiceImpl implements EmailService {
    
	@Autowired
    private JavaMailSender emailSender;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	private final String DEFAULT_RECEIPIENT = ""; 
	
	@Override
    public void sendOwnerWelcomeEmail(String owner) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(DEFAULT_RECEIPIENT);
        message.setSubject("Welcome to PetClinic!");
        
        final Context ctx = new Context();
//        ctx.setVariable("ownerid", owner.getId());
//        ctx.setVariable("recipientName", owner.getFirstName() + " " + owner.getLastName());
        
        message.setText(templateEngine.process("emailTemplates/OwnerCreatedEmailTemplates.html", ctx));
        
        emailSender.send(message);
    }
}
