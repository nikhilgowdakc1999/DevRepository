 package com.revolusys.Registration_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.revolusys.Registration_api.advice.RecordNotFoundException;
import com.revolusys.Registration_api.entities.Register;
import com.revolusys.Registration_api.repository.RegisterRepo;


public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private RegisterRepo regRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//load user from database 
		
		Register register=regRepo.findByemail(username).orElseThrow(()-> new RecordNotFoundException("User not found!!"));
		return new CustomUser(register);
	}

}
