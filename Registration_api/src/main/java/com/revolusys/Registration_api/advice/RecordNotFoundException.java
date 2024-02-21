package com.revolusys.Registration_api.advice;

public class RecordNotFoundException extends RuntimeException {

	public RecordNotFoundException(String msg)
	{
		super(msg);
	}

	public RecordNotFoundException() {
		super();
		
	}

	public RecordNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace); //important
		
	}

	public RecordNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RecordNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
}
