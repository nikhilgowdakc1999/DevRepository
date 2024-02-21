package com.revolusys.Registration_api.service;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;

import com.revolusys.Registration_api.entities.LogIn;
import com.revolusys.Registration_api.entities.Register;

public interface RegisterService {

	public List<Register> getAll();

	public Register add(Register register);
	
	public Register update(Map<String,Object> map, int id);

	public String deleteAll();

	public ResponseEntity<Object> RegisterLogin(LogIn log);

	public ResponseEntity<Object> updatePassword(LogIn log);

	
}
