package com.revolusys.Registration_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revolusys.Registration_api.entities.LogIn;
import com.revolusys.Registration_api.service.RegisterService;
import jakarta.validation.Valid;

@RestController
public class LogInController {

	@Autowired 
	private RegisterService regservice;
	
//	Login
	@PostMapping("/Login")
	public ResponseEntity<Object> login(@Valid @RequestBody LogIn log)
	{
		ResponseEntity<Object> in=regservice.RegisterLogin(log);
		return in;
	}
	
//  Forgot Password
	@PutMapping("/ForgotPassword")
	public ResponseEntity<Object> forgotPassword(@Valid @RequestBody LogIn log)
	{
		ResponseEntity<Object> fp=regservice.updatePassword(log);
		return fp;
	}
	
}
