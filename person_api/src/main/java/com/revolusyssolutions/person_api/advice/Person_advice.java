package com.revolusyssolutions.person_api.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Person_advice  {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map <String,String> handleInvalidArguments(MethodArgumentNotValidException ex )
	{
		Map<String,String> errorMap=new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->{
			errorMap.put(error.getField(),error.getDefaultMessage());
		});
		errorMap.put("error","Bad Request");
		errorMap.put("statuscode","400");
		return errorMap;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> ResourceNotFoundExceptionhandler(ResourceNotFoundException ce)
	{
		String message=ce.getMessage();
		ErrorResponse apiresponse=new ErrorResponse(message,false);	
		return new ResponseEntity<ErrorResponse>(apiresponse,HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.FOUND)
	@ExceptionHandler(ResourceAlreadyPresentException.class)
	public ResponseEntity<ErrorResponse> ResourceAlreadyPresentException(ResourceAlreadyPresentException re)
	{
		ErrorResponse apiresponse=new ErrorResponse(re.getMessage(),false);	
		return new ResponseEntity<ErrorResponse>(apiresponse,HttpStatus.FOUND);
	}
	
/*	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object>exceptionHandler(Exception e) 
	{ 
		Map<String,Object> body=new HashMap<>();
		body.put("message"," provide input in proper format");
		body.put("statuscode","400");
		body.put("error", "Bad Request"); 
		body.put("Success", false);
		return new ResponseEntity<Object>(body,HttpStatus.BAD_REQUEST ); 
	}

*/
}

