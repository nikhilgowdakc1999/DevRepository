package com.revolusyssolutions.person_api.Response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

	public static ResponseEntity<Object> generateResponse(String message,HttpStatus status,Object responseobj)
	{
		Map<String,Object> map=new HashMap<>();
		map.put("message", message);
		map.put("statuscode", status.value());
		map.put("data", responseobj);
		
		return new ResponseEntity<Object>(map,status);
	}
	
}