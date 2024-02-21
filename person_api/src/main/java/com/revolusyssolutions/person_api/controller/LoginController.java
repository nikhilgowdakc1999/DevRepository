package com.revolusyssolutions.person_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revolusyssolutions.person_api.entities.Login;
import com.revolusyssolutions.person_api.services.PersonService;

import jakarta.validation.Valid;

@RestController
public class LoginController {

	@Autowired
	private PersonService personservice;
	
/*	//Otp Generation
	@PostMapping("/generate-Otp")
	public String OtpGeneration(@Valid @RequestBody Login log)
	{
		String msg=this.personservice.OTPGeneration(log);
		return msg;
	}
	*/
	
	//Login with credentials
	@PostMapping("/Login")
	public String logIn (@Valid @RequestBody Login log)
	{
		String msg=this.personservice.RegisterLogin(log);
		return msg;
	}
	
	//resetting forgot password
	@PostMapping("/reset-password")
	public String resetpassword(@Valid @RequestBody Login log,@RequestParam String OTP)
	{
		String msg=this.personservice.updatePassword(log,OTP);
		return msg;
	}
	
}
