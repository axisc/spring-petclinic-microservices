package org.springframework.samples.petclinic.communication.email;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGridAPI;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailServiceImpl implements EmailService {

	@Autowired
	private SendGridAPI sendGridClient;
	
	@Autowired
	private SpringTemplateEngine templateEngine;

	@Value("${default.recipient}")
	private String DEFAULT_RECIPIENT;

	@Value("${email.active}")
	private boolean EMAIL_ACTIVE;


	@Override
    public void sendOwnerWelcomeEmail(String owner) throws IOException {
        // Extract owner information from the string.
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(owner);
        JsonNode idNode = rootNode.path("id");
        JsonNode firstNameNode = rootNode.path("firstName");
        JsonNode lastNameNode = rootNode.path("lastName");
        JsonNode emailNode = rootNode.path("email");
        
        // Create the context to share the information on the owner.
        final Context ctx = new Context();
        ctx.setVariable("ownerid", idNode.asInt());
        ctx.setVariable("recipientName", firstNameNode.asText() + " " + lastNameNode.asText());


        // Create the email
        String contentBody = templateEngine.process("OwnerCreatedEmailTemplates", ctx);
        Content content = new Content("text/html", contentBody);
        Mail mail;

        // Send the email
        if (EMAIL_ACTIVE) {
        	mail = new Mail(new Email("no-reply@springpetclinic.com"), "Welcome to PetClinic!", new Email(DEFAULT_RECIPIENT), content);
        	Request request = new Request();
        	request.setMethod(Method.POST);
        	request.setEndpoint("mail/send");
        	request.setBody(mail.build());
        	Response response = sendGridClient.api(request);
        	if (response.getStatusCode() == HttpStatus.ACCEPTED.value()) {
        		log.info("Sent mail with Subject '" + mail.getSubject() + "' to recipient '" + DEFAULT_RECIPIENT + "'");
    		} else {
    			log.info("Problem sending mail with Subject '" + mail.getSubject() + "' to recipient '" + DEFAULT_RECIPIENT + "'");
			}
        } else {
        	mail = new Mail(new Email("no-reply@springpetclinic.com"), "Welcome to PetClinic!", new Email(emailNode.asText()), content);
        	log.info("Fake sending mail with Subject '" + mail.getSubject() + "' to recipient '" + emailNode.asText() + "'");
        }
    }


	@Override
	public void sendVisitRecordEmail(String visitRecord) throws IOException {
		// Extract visit record from string.
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(visitRecord);
		JsonNode idNode = rootNode.path("id");
		JsonNode dateNode = rootNode.path("date");
		JsonNode descriptionNode = rootNode.path("description");
		JsonNode petIdNode = rootNode.path("petId");
		
		//Create the context to share the information on the owner.
		final Context ctx = new Context();
		ctx.setVariable("date", dateNode.asText());
		ctx.setVariable("description", descriptionNode.asText());
		
		// Create the email
        String contentBody = templateEngine.process("VisitRecordEmailTemplates", ctx);
        Content content = new Content("text/html", contentBody);
        Mail mail;
        
        // Send the email
        if (EMAIL_ACTIVE) {
        	mail = new Mail(new Email("no-reply@springpetclinic.com"), "Your recent visit to PetClinic!", new Email(DEFAULT_RECIPIENT), content);
        	Request request = new Request();
        	request.setMethod(Method.POST);
        	request.setEndpoint("mail/send");
        	request.setBody(mail.build());
        	Response response = sendGridClient.api(request);
        	if (response.getStatusCode() == HttpStatus.ACCEPTED.value()) {
        		log.info("Sent mail with Subject '" + mail.getSubject() + "' to recipient '" + DEFAULT_RECIPIENT + "'");
    		} else {
    			log.info("Problem sending mail with Subject '" + mail.getSubject() + "' to recipient '" + DEFAULT_RECIPIENT + "'");
			}
        } else {
        	mail = new Mail(new Email("no-reply@springpetclinic.com"), "Your recent visit to PetClinic!", new Email(DEFAULT_RECIPIENT), content);
        	log.info("Fake sending mail with Subject '" + mail.getSubject() + "' to recipient '" + DEFAULT_RECIPIENT + "'");
        }

	}
}
