package com.revolusyssolutions.person_api.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revolusyssolutions.person_api.entities.Person;
import com.revolusyssolutions.person_api.entities.services.PersonService;
import com.revolusyssolutions.person_api.repository.PersonRepo;

@SpringBootTest // loads full spring ApplicationContext including all the beans defined in application that will be used in our tests
public class ServiceTest {
	
	@Autowired
	private PersonService service;

	@MockBean  //used for mocking objects that are part of spring Context and replaces any existing Bean of same type in Applicationcontext
	private PersonRepo repository;

	@Test
	@Order(1)
	public void storePersonTest()
	{
		Person per=new Person(01, "John","john@gmail.com", "28","male", "9876543210", "India", "John@123");
		when(repository.save(any(Person.class))).thenReturn(per); //Here we are stubbing method to return data that we need
		assertEquals(per, service.addPerson(per));
	}

	@Test
	@Order(2)
	public void getAllPersonsTest()
	{
		//when is used to mock a method that return a specific value as output where then is used
		//In Simple terms, "when the XYZ() method is called, then return ABC"
		when(repository.findAll()).thenReturn(Stream.of(new Person(01, "John","john@gmail.com", "28","male", "9876543210", "India", "John@123"),
				new Person(02, "Sophie","sophie@gmail.com", "30","female", "8765432109", "USA", "Sophie@123")).toList());
		assertEquals(2,service.getAll().size() ); //assert methods are used for validating the outputs obtained

	}

	@Test
	@Order(3)
	public void getPersonTest()
	{
		int id=02;
		Optional<Person> per=Optional.of(new Person(02, "Sophie","sophie@gmail.com", "30","female", "8765432109", "USA", "Sophie@123"));
		when(repository.findById(id)).thenReturn(per);
		assertEquals(per,service.getPerson(id));
	}

	@Test
	@Order(4)
	public void updatePersonTest()
	{
		int id=01;
		Person existing=new Person(01, "John","john@gmail.com", "28","male", "9876543210", "India", "John@123");
		Person updated=new Person(01, "Sophie","sophie@gmail.com", "30","female", "8765432109", "USA", "Sophie@123");
		when(repository.findById(id)).thenReturn(Optional.of(existing));
		when(repository.save(any(Person.class))).thenReturn(updated);
		Person updatedPerson=service.updatePerson(updated, id);
		assertEquals(updated, updatedPerson);
	}

	@Test
	@Order(5)
	public void updatedataTest()
	{
		int id=01;
		Map<String,Object> updates=new HashMap<>();
		updates.put("name", "Sophie");
		updates.put("age", "30");

		when(repository.findById(id)).thenReturn(Optional.of(new Person()));
		Person updatedPerson=service.updatedata(updates, id);
		verify(repository,times(1)).save(any(Person.class)); // using Verify we can say how many times it is expected that the method is beaing called
	}

	@Test
	@Order(6)
	public void deletePersonTest()
	{
		int id=01;
		when(repository.existsById(id)).thenReturn(true);
		service.deletePerson(id);
		verify(repository,times(1)).deleteById(id);
	}

	@Test
	@Order(7)
	public void deleteAllPersonsTest()
	{
		service.deleteAllPersons();
		verify(repository,times(1)).deleteAll(); //times() specifies exact no of invocations
	}


}
