package com.revolusys.Registration_api.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DeleteResponseHandler {
	
	public static ResponseEntity<Object> generate(String message,HttpStatus statuscode,String status)
	{
		Map<String,Object> map=new HashMap<>();
		map.put("message", message);
		map.put("statuscode", statuscode.value());
		map.put("Status", status);

		return new ResponseEntity<Object>(map,statuscode);
	}

}
