package org.springframework.samples.petclinic.communication.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BrowserResource {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	
	@GetMapping("/browser/{queue}")
	public String index(@PathVariable String queue,Model model) {

		List<OwnerDetails> owners = jmsTemplate.browse(queue, new BrowserCallback<List<OwnerDetails>>() {

			@Override
			public List<OwnerDetails> doInJms(Session session, QueueBrowser browser) throws JMSException {
				Enumeration messages = browser.getEnumeration();
				List<OwnerDetails> owners = new ArrayList<OwnerDetails>();

				while (messages.hasMoreElements()) {
					TextMessage message = (TextMessage) messages.nextElement();
					owners.add(new OwnerDetails(message.getText().substring(message.getText().indexOf("{"))));
				}
				return owners;
			}
			
		});
		model.addAttribute("queueName", queue);
		model.addAttribute("owners", owners);

		return "browser";
	}
	
	private class OwnerDetails {
		String id;
		String firstName;
		String lastName;
		String address;
		String city;
		String telephone;
		String email;

		public OwnerDetails(String ownerString) {
			ObjectMapper objectMapper = new ObjectMapper();
	        try {
	        	JsonNode rootNode = objectMapper.readTree(ownerString);
	        	this.id = rootNode.path("id").asText();
	        	this.firstName = rootNode.path("firstName").asText();
	        	this.lastName = rootNode.path("lastName").asText();
	        	this.address = rootNode.path("address").asText();
	        	this.city = rootNode.path("city").asText();
	        	this.telephone = rootNode.path("telephone").asText();
	        	this.email = rootNode.path("email").asText();
	        } catch (Exception e) {
	        	return;
	        }
	        
		}

		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
	}
}
