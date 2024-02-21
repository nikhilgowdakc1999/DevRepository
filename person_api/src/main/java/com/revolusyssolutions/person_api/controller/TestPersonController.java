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
public class TestPersonController {

	@Autowired
	private PersonService personservice;

	//fetching all available persons data
	@GetMapping("/getpersonsTest")
	public List<Person>  getAllPersonsTest()
	{
		List<Person> persons=personservice.getAll() ;
		if(persons.isEmpty())
		{
			throw new ResourceNotFoundException("No Records found!!");	
		}
		return persons;
	}

	//fetching a particular person
	@GetMapping("/getpersonTest/{id}")
	public Optional<Person> getTest(@PathVariable int id) 
	{
		Optional<Person> per= personservice.getPerson(id);
		if( per.isPresent())
		{
			System.out.println(per.get());
			return  per;
		}
		throw new ResourceNotFoundException("person not found with id: "+id);
	}

	//Creating a new person data
	@PostMapping("/savepersonTest")
	public Person createPersonTest(@Valid @RequestBody Person person )
	{
		Person per=this.personservice.addPerson(person);	
		return per;
	}



	//Update existing person data
	@PutMapping("/updatepersonTest/{id}")
	public Person updateTest(@Valid @RequestBody Person person,@ PathVariable int id)
	{
		Person per=personservice.updatePerson(person, id);	
		return per;
	}


	//update a particular record of a particular person
	@PatchMapping("/updatedataTest/{id}")
	public Person update1Test(@RequestBody Map<String,Object> map ,@PathVariable int id)
	{
		Person per=personservice.updatedata(map, id);	
		return per;
	}	

	//Delete a particular person detail
	@DeleteMapping("/deletepersonTest/{id}")
	public String deleteTest(@PathVariable int id)
	{
		personservice.deletePerson(id);
		return "person data deleted succesfully!!";
	}

	//Delete all existing records
	@DeleteMapping("/deletepersonsTest")
	public String deleteAllTest()
	{
		this.personservice.deleteAllPersons();
		return ("All records deleted!!");
	}
}
