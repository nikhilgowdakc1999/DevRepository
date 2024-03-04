package jwtphoneno.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jwtphoneno.entity.AuthRequest;
import jwtphoneno.entity.UserCredential;
import jwtphoneno.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	    @Autowired
	    private AuthService service;

	    @PostMapping("/register")
	    public String addNewUser(@RequestBody UserCredential user) {
	        return service.saveUser(user);
	    }

	    @PostMapping("/token")
	    public String getToken(@RequestBody AuthRequest authRequest) {
	   
	       return service.generateToken2(authRequest);
	          
	    }

	    @GetMapping("/validate")
	    public String validateToken(@RequestParam("token") String token) {
	     if( service.validateToken(token))
	     {
	    	 return "Token is valid";
	     }else
	     {
	    	 return "Token Invali!!";
	     }
	        
	    }
	    
	    @GetMapping("/get-all")
	    public List<UserCredential> allUsers()
	    {
	    	return this.service.getAll();
	    }
	    
	    @DeleteMapping("/delete-all")
	    public String deleteAllUsers()
	    {
	    	return this.service.deleteAll();
	    }
	
}
