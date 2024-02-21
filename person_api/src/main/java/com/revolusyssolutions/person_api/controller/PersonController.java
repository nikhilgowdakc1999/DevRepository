package com.revolusyssolutions.person_api.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	@GetMapping("/getpersons")
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
	@GetMapping("/getperson/{id}")
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
	@PostMapping("/saveperson")
	public ResponseEntity<Object> createPerson(@Valid @RequestBody Person person )
	{
		Person per=this.personservice.addPerson(person);	
		return ResponseHandler.generateResponse("person data added succesfully!!", HttpStatus.CREATED, per);
	}



	//Update existing person data
	@PutMapping("/updateperson/{id}")
	public ResponseEntity<Object> update(@Valid @RequestBody Person person,@ PathVariable int id)
	{
		Person per=personservice.updatePerson(person, id);	
		return ResponseHandler.generateResponse("person data updated succesfully!!", HttpStatus.CREATED, per);
	}


	//update a particular record of a particular person
	@PatchMapping("/updatedata/{id}")
	public ResponseEntity<Object> update1(@RequestBody Map<String,Object> map ,@PathVariable int id)
	{
		Person per=personservice.updatedata(map, id);	
		return ResponseHandler.generateResponse("person data updated succesfully!!", HttpStatus.CREATED, per);
	}

	//Delete a particular person detail
	@DeleteMapping("/deleteperson/{id}")
	public ResponseEntity<Object> delete(@PathVariable int id)
	{
		personservice.deletePerson(id);
		return ResponseHandler.generateResponse("person data deleted succesfully!!",HttpStatus.NO_CONTENT,null);
	}

	//Delete all existing records
	@DeleteMapping("/deletepersons")
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
