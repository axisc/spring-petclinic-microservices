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

		List<String> messages = jmsTemplate.browse(queue, new BrowserCallback<List<String>>() {

			@Override
			public List<String> doInJms(Session session, QueueBrowser browser) throws JMSException {
				Enumeration messages = browser.getEnumeration();
				
				List<String> processedMessages = new ArrayList<String>();
				
				while (messages.hasMoreElements()) {
					TextMessage message = (TextMessage) messages.nextElement();
					processedMessages.add(message.getText().substring(message.getText().indexOf("{")));
				}
				return processedMessages;
			}
			
		});
		model.addAttribute("queueName", queue);
		model.addAttribute("messageData", messages);

		return "browser";
	}
}
