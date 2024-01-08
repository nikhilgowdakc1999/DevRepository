package com.revolusys.Registration_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.revolusys.Registration_api.entities.LogIn;
import com.revolusys.Registration_api.entities.Register;

import jakarta.validation.Valid;

public interface RegisterService {

	public List<Register> getAll();

//	public Optional<Register> get(int id);

	public Register add(Register register);

//	public Register update(Register register,int id);

//	public String delete(int id);

	public String deleteAll();

	public ResponseEntity<Object> RegisterLogin(@Valid LogIn log);

	public ResponseEntity<Object> updatePassword(@Valid LogIn log);
}