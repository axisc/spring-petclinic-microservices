package org.springframework.samples.petclinic.communication;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.samples.petclinic.communication.email.EmailService;
import org.springframework.samples.petclinic.communication.email.EmailServiceImpl;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridAPI;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

	@Value("${sendgrid.api.key}") String sendGridAPIKey;

	@Bean
	public EmailService getEmailService() {
		return new EmailServiceImpl();
	}

	@Bean
	public SendGridAPI getSendGridApi() {
		return new SendGrid(sendGridAPIKey);
	}

	@Bean
	public SpringTemplateEngine getSpringTemplateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addTemplateResolver(htmlTemplateResolver());
		return templateEngine;
	}

	private ITemplateResolver htmlTemplateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("/emailTemplates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("UTF8");
		templateResolver.setCheckExistence(true);
		templateResolver.setCacheable(false);
		return templateResolver;
	}
}
