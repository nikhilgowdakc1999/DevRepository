package jwtphoneno.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jwtphoneno.entity.UserCredential;
import jwtphoneno.repository.UserCredentialRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	 @Autowired
	    private UserCredentialRepository repository;
/*
	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	      UserCredential credential = repository.findByPhoneno(username).orElseThrow(()-> new RuntimeException("User not found!!"));
	        return new CustomUserDetails(credential);
	*/        
	        @Override
		    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		      UserCredential credential = repository.findByEmail(username).orElseThrow(()-> new RuntimeException("User not found!!"));
		        return new CustomUserDetails(credential);
		        
	        
	        
	    }
	
}
