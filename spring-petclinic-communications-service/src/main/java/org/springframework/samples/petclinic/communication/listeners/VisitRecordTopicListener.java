package org.springframework.samples.petclinic.communication.listeners;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.samples.petclinic.communication.email.EmailService;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class VisitRecordTopicListener {

	private static final String TOPIC_NAME = "visitRecordTopic";
	
	@Autowired
	private EmailService emailService;
	
	@JmsListener(destination = TOPIC_NAME, subscription = "visitRecordSubscriber", containerFactory = "topicJmsListenerContainerFactory")
	public void receiveMessage(String visitRecordString) throws IOException {
		// remove the header information
		String refinedVisitRecordString = visitRecordString.substring(visitRecordString.indexOf("{"));
		
		emailService.sendVisitRecordEmail(refinedVisitRecordString);
	}
}
