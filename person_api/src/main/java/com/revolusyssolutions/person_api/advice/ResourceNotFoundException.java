package com.revolusyssolutions.person_api.advice;

public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException(String message) 
	{
		super(message);
	}

}
