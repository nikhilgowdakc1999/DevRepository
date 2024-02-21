package com.revolusys.Registration_api.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Responsehandler {

	public static ResponseEntity<Object> generateResponse(String message,HttpStatus statuscode,Object responseobj,String status)
	{
		Map<String,Object> map=new HashMap<>();
		map.put("message", message);
		map.put("statuscode", statuscode.value());
		map.put("data", responseobj);
		map.put("Status", status);

		return new ResponseEntity<Object>(map,statuscode);
	}
	
}
