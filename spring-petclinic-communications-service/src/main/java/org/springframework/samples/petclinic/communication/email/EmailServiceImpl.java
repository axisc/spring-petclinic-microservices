package org.springframework.samples.petclinic.communication.email;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridAPI;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

public class EmailServiceImpl implements EmailService {
    
	@Autowired
	private SendGridAPI sendGridClient;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
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
        Mail mail = new Mail(new Email("no-reply@springpetclinic.com"), "Welcome to PetClinic!", new Email(emailNode.asText()), content);

        // Send the email
        Request request = new Request();
    	request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sendGridClient.api(request);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        System.out.println(response.getHeaders());
    }
}
