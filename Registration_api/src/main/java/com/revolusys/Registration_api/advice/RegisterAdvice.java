package com.revolusys.Registration_api.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RegisterAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map <String,String> handleInvalidArguments(MethodArgumentNotValidException ex)
	{
		Map<String,String> errorMap=new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->{
			errorMap.put(error.getField(),error.getDefaultMessage());
		});
		errorMap.put("status","Failed");
		errorMap.put("statuscode","400");
		return errorMap;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ResponseType> RecordNotFoundExceptionhandler(RecordNotFoundException ce)
	{
		String message=ce.getMessage();
		ResponseType apiresponse=new ResponseType(message,false);
		return new ResponseEntity<ResponseType>(apiresponse,HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.FOUND)
	@ExceptionHandler(RecordAlreadyPresentException.class)
	public ResponseEntity<ResponseType> ResourceAlreadyPresentException(RecordAlreadyPresentException re)
	{
		ResponseType apiresponse=new ResponseType(re.getMessage(),false);	
		return new ResponseEntity<ResponseType>(apiresponse,HttpStatus.FOUND);
	}
/*
	@ResponseStatus(HttpStatus.BAD_REQUEST) 
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> exceptionHandler(Exception e)
	{ 
		Map<String,Object> body=new HashMap<>();
		body.put("message","Please provide input in proper format");
		body.put("statuscode","400");
		body.put("error", "Bad Request"); 
		return new ResponseEntity<Object>(body,HttpStatus.BAD_REQUEST ); 	  
	}
	
	*/
}
