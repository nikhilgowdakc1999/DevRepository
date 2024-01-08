package com.revolusys.Registration_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.revolusys.Registration_api.advice.RecordAlreadyPresentException;
import com.revolusys.Registration_api.advice.RecordNotFoundException;
import com.revolusys.Registration_api.entities.LogIn;
import com.revolusys.Registration_api.entities.Register;
import com.revolusys.Registration_api.repository.RegisterRepo;
import com.revolusys.Registration_api.response.Responsehandler;
import jakarta.validation.Valid;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private RegisterRepo repo;
	
	@Override
	public List<Register> getAll() 
	{
		return this.repo.findAll();
	}

	@Override
	public Register add(Register register) {
		Optional <Register> storedData=repo.findByemail(register.getEmail());
		if(storedData.isPresent()) throw new RecordAlreadyPresentException("Record already present!!");
		repo.save(register);
		return register;
	}
	
	@Override
	public String deleteAll() {
		repo.deleteAll();
		return "All records Deleted";
	}

	@Override
	public ResponseEntity<Object> RegisterLogin(@Valid LogIn log) {
		
		Optional<Register> existingRecord=repo.findByemail(log.getEmail());
		if(existingRecord.isPresent()) 
		{
			if(log.getPassword().equals(existingRecord.get().getPassword()))
			{
				 return Responsehandler.generateResponse("Login Succesfull!!", HttpStatus.OK, log);
			}else
			{
				throw new RecordNotFoundException("Password incorrect!!");
			}	
		}else
		{
			throw new RecordNotFoundException("User doesn't exist with provided email!!");
		}
		
	}

	@Override
	public ResponseEntity<Object> updatePassword(@Valid LogIn log) {
		
		Optional<Register> existingperson=repo.findByemail(log.getEmail());
		if(existingperson.isPresent()) 
		{
				existingperson.get().setPassword(log.getPassword());
				Register reg=repo.save(existingperson.get());
				return Responsehandler.generateResponse("Password changed succesfully", HttpStatus.OK, reg);
		}
		else 
		{
			throw new RecordNotFoundException("User doesn't exist with provided email!!");
		}	
	}

}
