package com.revolusyssolutions.person_api.entities.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.revolusyssolutions.person_api.Response.ResponseHandler;
import com.revolusyssolutions.person_api.advice.ResourceAlreadyPresentException;
import com.revolusyssolutions.person_api.advice.ResourceNotFoundException;
import com.revolusyssolutions.person_api.entities.Login;
import com.revolusyssolutions.person_api.entities.Person;
import com.revolusyssolutions.person_api.repository.PersonRepo;

@Service
public class PersonServiceImpl implements PersonService{

	@Autowired
	private PersonRepo repo;

	@Override
	public List<Person> getAll()
	{
		return this.repo.findAll();
	}

	@Override
	public Optional<Person> getPerson(int id)
	{
		return this.repo.findById(id);
	}

	@Override
	public Person addPerson(Person person) {
	    Optional<Person> storedvalue=repo.findByemail(person.getEmail());
		if(storedvalue.isPresent())throw new ResourceAlreadyPresentException("Record already present!!");
	    repo.save(person);
	    return person;
	}

	@Override
	public ResponseEntity<Object> loginPerson(Login login) {
        
		Optional<Person> existingperson=repo.findByemail(login.getEmail());
		if(existingperson.isPresent()) 
		{
			if(login.getPassword().equals(existingperson.get().getPassword()))
			{
				 return ResponseHandler.generateResponse("Login Succesfull!!", HttpStatus.OK, login);
			}else
			{
				throw new ResourceNotFoundException("Password incorrect!!");
			}	
		}else
		{
			throw new ResourceNotFoundException("User doesn't exist with provided email!!");
		}
	}

	
	
	@Override
	public ResponseEntity<Object> updatePassword(Login login) {
		
		Optional<Person> existingperson=repo.findByemail(login.getEmail());
		if(existingperson.isPresent()) 
		{
				existingperson.get().setPassword(login.getPassword());
				Person per=repo.save(existingperson.get());
				return ResponseHandler.generateResponse("Password changed succesfully", HttpStatus.OK, per);
		}
		else 
		{
			throw new ResourceNotFoundException("User doesn't exist with provided email!!");
		}	
	}
	

	@Override
	public Person updatePerson(Person person, int id)
	{	
		return repo.saveAndFlush(person);
	}

	@Override
	public void deletePerson(int id)
	{
		repo.deleteById(id);
	}

	@Override
	public void deleteAllPersons()
	{
		repo.deleteAll();	
	}

	

}
