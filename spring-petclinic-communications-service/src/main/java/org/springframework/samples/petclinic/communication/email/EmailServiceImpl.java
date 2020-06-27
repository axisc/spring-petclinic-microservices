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
        Content content = new Content("text/richtext", templateEngine.process("emailTemplates/OwnerCreatedEmailTemplates.html", ctx));
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
        		System.out.println("Sent mail with Subject '" + mail.getSubject() + "' to recipient '" + DEFAULT_RECIPIENT + "'");
    		} else {
    			System.out.println("Problem sending mail with Subject '" + mail.getSubject() + "' to recipient '" + DEFAULT_RECIPIENT + "'");
			}
        } else {
        	mail = new Mail(new Email("no-reply@springpetclinic.com"), "Welcome to PetClinic!", new Email(emailNode.asText()), content);
        	System.out.println("Fake sending mail with Subject '" + mail.getSubject() + "' to recipient '" + emailNode.asText() + "'");
        }
    }
}
