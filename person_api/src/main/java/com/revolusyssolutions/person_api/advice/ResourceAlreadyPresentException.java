package com.revolusyssolutions.person_api.advice;

public class ResourceAlreadyPresentException extends RuntimeException{
	
	public ResourceAlreadyPresentException(String msg)
	{
		super(msg);
	}
}
