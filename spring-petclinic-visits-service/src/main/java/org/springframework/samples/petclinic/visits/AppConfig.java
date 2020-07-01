package org.springframework.samples.petclinic.visits;

import javax.jms.Topic;

import org.apache.qpid.jms.JmsTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	@Bean
	public Topic getTopic() {
		return new JmsTopic("visitRecordTopic");
	}

}
