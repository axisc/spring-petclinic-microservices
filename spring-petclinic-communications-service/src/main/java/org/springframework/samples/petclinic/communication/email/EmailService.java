package org.springframework.samples.petclinic.communication.email;

import org.springframework.samples.petclinic.customers.model.Owner;

public interface EmailService {

	void sendOwnerWelcomeEmail(Owner owner);

}
