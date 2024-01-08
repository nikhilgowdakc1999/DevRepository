package com.revolusys.Registration_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revolusys.Registration_api.advice.RecordNotFoundException;
import com.revolusys.Registration_api.entities.Register;
import com.revolusys.Registration_api.response.Responsehandler;
import com.revolusys.Registration_api.service.RegisterService;
import jakarta.validation.Valid;

@RestController
public class RegisterController {

	@Autowired
	private RegisterService regservice;

	//fetching all available Registration data
	@GetMapping("/Register")
	public ResponseEntity<Object>  getAllRegisters()
	{
		List<Register> registers=regservice.getAll() ;
		if(registers.isEmpty())
		{
			throw new RecordNotFoundException("No Records found!!");	
		}
		return Responsehandler.generateResponse("All records retrived succesfully!!", HttpStatus.OK,registers);
	}


	//Creating a new Registration data
	@PostMapping("/Register")
	public ResponseEntity<Object> createRecord(@Valid @RequestBody Register register)
	{
		Register reg=this.regservice.add(register);	
		return Responsehandler.generateResponse("Person data added succesfully!!", HttpStatus.CREATED, reg);
	}

	//Delete all Registration data
	@DeleteMapping("/Register")
	public ResponseEntity<String> deleteAll()
	{
		this.regservice.deleteAll();
		return new ResponseEntity<String>("All records deleted!!",HttpStatus.NO_CONTENT);
	}


}
