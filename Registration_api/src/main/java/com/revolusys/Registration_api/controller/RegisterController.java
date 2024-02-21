package com.revolusys.Registration_api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revolusys.Registration_api.advice.RecordNotFoundException;
import com.revolusys.Registration_api.entities.Register;
import com.revolusys.Registration_api.response.DeleteResponseHandler;
import com.revolusys.Registration_api.response.Responsehandler;
import com.revolusys.Registration_api.service.RegisterService;
import jakarta.validation.Valid;

@RestController
public class RegisterController {

	@Autowired
	private RegisterService regservice;

	//fetching all available Registration data
	@GetMapping("/FetchRecords")
	public ResponseEntity<Object>  getAllRegisters()
	{
		List<Register> registers=regservice.getAll() ;
		if(registers.isEmpty())
		{
			throw new RecordNotFoundException("No Records found!!");	
		}
		return Responsehandler.generateResponse("All records retrived succesfully!!", HttpStatus.OK,registers,"success");
	}


	//Creating a new Registration data
	@PostMapping("/SaveRecord")
	public ResponseEntity<Object> createRecord(@Valid @RequestBody Register register)
	{
		Register reg=this.regservice.add(register);	
		return Responsehandler.generateResponse("Person data added succesfully!!", HttpStatus.CREATED, reg,"success");
	}
	
	//updating data in an existing record
	@PatchMapping("/updateRecord")
	public ResponseEntity<Object> updateRecord(@RequestBody Map<String,Object> map,int id)
	{
		Register reg=this.regservice.update(map,id);
		return Responsehandler.generateResponse("Person data updated succesfully!!", HttpStatus.CREATED, reg, "success");
		
	}

	//Delete all Registration data
	@DeleteMapping("/DeleteRecords")
	public ResponseEntity<Object> deleteAll()
	{
		List<Register> registers=regservice.getAll() ;
		if(registers.isEmpty())
		{
			throw new RecordNotFoundException("No Records found to delete!!");	
		}
		regservice.deleteAll();
		return  DeleteResponseHandler.generate("All records deleted succesfully!!", HttpStatus.NO_CONTENT, "success");
	}
	
}
