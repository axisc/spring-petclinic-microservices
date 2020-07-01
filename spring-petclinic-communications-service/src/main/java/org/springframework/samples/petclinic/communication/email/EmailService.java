package org.springframework.samples.petclinic.communication.email;

import java.io.IOException;

public interface EmailService {

	void sendOwnerWelcomeEmail(String owner) throws IOException;

	void sendVisitRecordEmail(String visitRecord) throws IOException;

}
