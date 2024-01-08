package com.revolusyssolutions.person_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revolusyssolutions.person_api.Response.ResponseHandler;
import com.revolusyssolutions.person_api.advice.ResourceNotFoundException;
import com.revolusyssolutions.person_api.entities.Person;
import com.revolusyssolutions.person_api.entities.services.PersonService;
import jakarta.validation.Valid;

@RestController

public class PersonController {

	@Autowired
	private PersonService personservice;

	//fetching all available persons data
	@GetMapping("/persons")
	public ResponseEntity<Object>  getAllPersons()
	{
		List<Person> persons=personservice.getAll() ;
		if(persons.isEmpty())
		{
			throw new ResourceNotFoundException("No Records found!!");	
		}
		return ResponseHandler.generateResponse("Persons data retrived succesfully!!", HttpStatus.OK,persons);
	}

	//fetching a particular person
	@GetMapping("/persons/{id}")
	public ResponseEntity<Object> get(@PathVariable int id) 
	{
		Optional<Person> per= personservice.getPerson(id);
		if( per.isPresent())
		{
			System.out.println(per.get());
			return ResponseHandler.generateResponse("person data retrieved succesfully!!",HttpStatus.OK, per.get());
		}
		throw new ResourceNotFoundException("person not found with id: "+id);
	}

	//Creating a new person data
	@PostMapping("/Register")
	public ResponseEntity<Object> createPerson(@Valid @RequestBody Person person )
	{
		Person per=this.personservice.addPerson(person);	
		return ResponseHandler.generateResponse("Person data added succesfully!!", HttpStatus.CREATED, per);
	}



	//Update existing person data
	@PutMapping("/Register/{id}")
	public ResponseEntity<Object> update(@Valid @RequestBody Person person,@ PathVariable int id)
	{
		List<Person> Persons=personservice.getAll();
		Person per=null;
		for(Person p:Persons)
		{
			if(p.getId()==id)
			{
				per=personservice.updatePerson(person, id);	
			}
			else
			{
				throw new ResourceNotFoundException("Person data Not Found with id: "+id);
			}
		}
		return ResponseHandler.generateResponse("person data updated succesfully!!", HttpStatus.CREATED, per);
	}

	//Delete a particular person detail
	@DeleteMapping("/persons/{id}")
	public ResponseEntity<Object> delete(@PathVariable int id)
	{
		Optional<Person> per= personservice.getPerson(id);
		if( per.isPresent())
		{
			personservice.deletePerson(id);
			return ResponseHandler.generateResponse("person data deleted succesfully!!",HttpStatus.NO_CONTENT,null);
		}
		throw new ResourceNotFoundException("person not found with id: "+id);
	}

	//Delete all existing records
	@DeleteMapping("/persons")
	public String deleteAll()
	{
		List<Person> persons=personservice.getAll() ;
		if(persons.isEmpty())
		{
			throw new ResourceNotFoundException("No Records found to delete!!");	
		}
		this.personservice.deleteAllPersons();
		return ("All records deleted!!");
	}

}