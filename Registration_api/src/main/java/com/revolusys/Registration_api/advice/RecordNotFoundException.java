package com.revolusys.Registration_api.advice;

public class RecordNotFoundException extends RuntimeException {

	public RecordNotFoundException(String msg)
	{
		super(msg);
	}
}
