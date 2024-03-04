package com.revolusys.Registration_api.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revolusys.Registration_api.advice.RecordAlreadyPresentException;
import com.revolusys.Registration_api.advice.RecordNotFoundException;
import com.revolusys.Registration_api.config.JwtHelper;
import com.revolusys.Registration_api.entities.LogIn;
import com.revolusys.Registration_api.entities.Register;
import com.revolusys.Registration_api.repository.RegisterRepo;
import com.revolusys.Registration_api.response.JwtResponse;
import com.revolusys.Registration_api.response.Responsehandler;

import jakarta.servlet.http.Cookie;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtHelper helper;

	@Autowired
	private PasswordEncoder passwordEncoder; //imp to encrypt password

	@Autowired
	private RegisterRepo repo;
	
	@Autowired
	EmailService emailService;
	
	String generatedOtp;

	@Override
	public List<Register> getAll() 
	{
		return this.repo.findAll();
	}

	@Override
	public Register add(Register register) {
		Optional <Register> storedData=repo.findByemail(register.getEmail());
		if(storedData.isPresent()) throw new RecordAlreadyPresentException("Record already present!!");
		register.setPassword(passwordEncoder.encode(register.getPassword()));
		repo.save(register);
		return register;
	}

	@Override
	public ResponseEntity<Object> RegisterLogin(LogIn log) {

		Optional<Register> existingRecord=repo.findByemail(log.getEmail());
		if(existingRecord.isPresent()) 
		{
			if(passwordEncoder.matches(log.getPassword(), existingRecord.get().getPassword()))
			{
				/*
				Random r = new Random();
				generatedOtp = String.format("%06d", r.nextInt(100000));
				String subject = "Email Verfication";
				String body = "Your verification OTP is "+generatedOtp;
				//Email Send
				this.emailService.sendEmail(existingRecord.get().getEmail(), subject, body);
				*/
				
				UserDetails userDetails=userDetailsService.loadUserByUsername(log.getEmail());
				String token=this.helper.generateToken(userDetails);
                
				Cookie tokenCookie=new Cookie("Cookie",token);
				tokenCookie.setPath("/"); // accessible throughout the domain
				tokenCookie.setSecure(true);
				
				JwtResponse response=JwtResponse.builder()
						                        .jwtToken(tokenCookie)
						                        .username(userDetails.getUsername()).build();
				
				return Responsehandler.generateResponse("Login Succesfull!!", HttpStatus.OK, response , "success");
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
	public ResponseEntity<Object> updatePassword(LogIn log) {

		Optional<Register> existingperson=repo.findByemail(log.getEmail());
		if(existingperson.isPresent()) 
		{
			existingperson.get().setPassword(passwordEncoder.encode(log.getPassword()));
			Register reg=repo.save(existingperson.get());
			return Responsehandler.generateResponse("Password changed succesfully", HttpStatus.OK, reg ,"success");
		}
		else 
		{
			throw new RecordNotFoundException("User doesn't exist with provided email!!");
		}	
	}


	@Override
	public String deleteAll() {
		repo.deleteAll();
		generatedOtp=null;
		return "All records Deleted";
	}

	@Override
	public Register update(Map<String,Object> map, int id)
	{
		Optional<Register> existingrecord=repo.findById(id);
		if(existingrecord.isPresent())
		{
			map.forEach((key,value)->{
				switch(key) {
				case "name":
					 existingrecord.get().setName((String) value);
					 break;
				case "email":
					 existingrecord.get().setName((String) value);
					 break;
				case "age":
					 existingrecord.get().setName((String) value);
					 break;
				case "gender":
					 existingrecord.get().setName((String) value);
					 break;
				case "phoneno":
					 existingrecord.get().setName((String) value);
					 break;
				case "country":
					 existingrecord.get().setName((String) value);
					 break;
				case "password":
					 existingrecord.get().setName((String) value);
					 break;
				}
				
			});
			
			return repo.save(existingrecord.get());
		}
		else
		{
			throw new RecordNotFoundException("Record Not Found with id: "+id);
		}
		
	}

}
