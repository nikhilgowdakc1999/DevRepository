package com.revolusys.Registration_api.advice;

public class RecordAlreadyPresentException extends RuntimeException {

	public RecordAlreadyPresentException(String message)
	{
		super(message);
	}
	
}
