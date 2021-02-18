package com.prc391.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prc391.models.Activation;
import com.prc391.repositories.ActivationRepository;
import com.prc391.utils.MailActivator;

@Service
public class ActivationServiceImpl {

	@Autowired
	private ActivationRepository activationRepo;
	
	public String createActivationRecord(String email) {
		String code = MailActivator.generateActivatedCode();
		Activation activation = new Activation(email, code);
		activationRepo.save(activation);
		return code;
	}
}
