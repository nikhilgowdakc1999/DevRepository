package com.revolusyssolutions.person_api.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.revolusyssolutions.person_api.entities.Login;
import com.revolusyssolutions.person_api.entities.Person;

public interface PersonService {

	public List<Person> getAll();
	
	public Optional<Person> getPerson(int id);
	
	public Person addPerson(Person person);

	public Person updatePerson(Person person,int id);
	
	public Person updatedata(Map<String,Object> map ,int id);
	
	public void deletePerson(int id);
	
	public void deleteAllPersons();
	
	public String OTPGeneration(Login log);
	
	public String RegisterLogin(Login log,String otp);

	public String updatePassword(Login log,String otp);

	
}
