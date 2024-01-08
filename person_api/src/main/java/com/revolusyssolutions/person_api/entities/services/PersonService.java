package com.revolusyssolutions.person_api.entities.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.revolusyssolutions.person_api.entities.Login;
import com.revolusyssolutions.person_api.entities.Person;

public interface PersonService {

	public List<Person> getAll();
	
	public Optional<Person> getPerson(int id);
	
	public Person addPerson(Person person);

	public Person updatePerson(Person person,int id);
	
    public ResponseEntity<Object> loginPerson(Login login);
	
	public ResponseEntity<Object> updatePassword(Login login);
	
	public void deletePerson(int id);
	
	public void deleteAllPersons();
	
}