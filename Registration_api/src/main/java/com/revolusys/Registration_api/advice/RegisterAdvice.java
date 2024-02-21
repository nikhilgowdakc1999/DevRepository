package com.revolusys.Registration_api.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // to create a Global ExceptionHandler that returns a json or Xml Response
public class RegisterAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST) //marks a method or Exception class with the statuscode 
	@ExceptionHandler(MethodArgumentNotValidException.class) // to handle exception thrown by a specific controller method 
	public Map <String,Object> handleInvalidArguments(MethodArgumentNotValidException ex)
	{
		Map<String, Object> errorMap=new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->{
			errorMap.put(error.getField(),error.getDefaultMessage()); // extract fields and their corresponding error message ,then place fields as Key and messages as Value inside map  
		});
		errorMap.put("status","Failed");
		errorMap.put("statuscode", 400);
		return errorMap;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ResponseType> RecordNotFoundExceptionhandler(RecordNotFoundException ce)
	{
		String message=ce.getMessage();
		ResponseType apiresponse=new ResponseType(message,"failed",HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseType>(apiresponse,HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.FOUND)
	@ExceptionHandler(RecordAlreadyPresentException.class)
	public ResponseEntity<ResponseType> ResourceAlreadyPresentException(RecordAlreadyPresentException re)
	{
		ResponseType apiresponse=new ResponseType(re.getMessage(), "failed", HttpStatus.FOUND.value());
		return new ResponseEntity<ResponseType>(apiresponse,HttpStatus.FOUND);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST) 
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> exceptionHandler(RuntimeException e)
	{ 
		Map<String,Object> body=new HashMap<>();
		body.put("message","Please provide input in proper format");
		body.put("statuscode",400);
		body.put("status", "failed"); 
		return new ResponseEntity<Object>(body,HttpStatus.BAD_REQUEST ); 	  
	}
	
}
