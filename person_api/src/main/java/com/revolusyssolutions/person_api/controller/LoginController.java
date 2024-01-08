package com.revolusyssolutions.person_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revolusyssolutions.person_api.entities.Login;
import com.revolusyssolutions.person_api.entities.services.PersonService;

import jakarta.validation.Valid;


@RestController
public class LoginController {

	@Autowired
	private PersonService personservice;

	//Person login

	@PostMapping("/login")
	public ResponseEntity<Object> personLogin(@Valid @RequestBody Login login)
	{
		ResponseEntity<Object> in=personservice.loginPerson(login);
		return in;
	}

	//Person forgot password

	@PutMapping("/forgotpassword")
	public ResponseEntity<Object> forgotPassword(@Valid @RequestBody Login login)
	{
		ResponseEntity<Object> fp=personservice.updatePassword(login);
		return fp;
	}

}


