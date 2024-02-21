package com.revolusyssolutions.person_api.integrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.revolusyssolutions.person_api.entities.Person;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

	@LocalServerPort //injects HTTP Server Port that was allocated at runtime and is particularly useful when using random port for testing
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate; // used to make Http requests to the dynamically assigned port

	@Autowired
	private TestH2Repository repo;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@AfterEach
	public void afterSetup() {
		repo.deleteAll();
	}


	@Test
	public void createRegisterTest()
	{

		baseUrl = baseUrl + ":" + port + "/savepersonTest";
		Person per= new Person(01,"John","john@gmail.com","28","male","9876543210","India", "John@123");

		Person person1=restTemplate.postForObject(baseUrl, per, Person.class);
		System.out.println(person1);
		assertNotNull(person1);
		assertEquals(person1.getName(),"John");
		assertEquals(1, repo.findAll().size());

	}

	@Test
	public void getRegisterByIdTest()
	{
		baseUrl = baseUrl + ":" + port + "/getpersonTest/{id}";
		Person per1= new Person(01,"John","john@gmail.com","28","male","9876543210","India", "John@123");
		Person saved=repo.save(per1);

		Person existingPerson=restTemplate.getForObject(baseUrl,Person.class,per1.getId());
		System.out.println(existingPerson);
		assertNotNull(existingPerson);
		assertEquals(saved.getId(),existingPerson.getId());

	}

	@Test
	public void getRegistersTest()
	{
		baseUrl = baseUrl + ":" + port + "/getpersonsTest";
		List<Person> persons= Stream.of(new Person(01, "John","john@gmail.com", "28","male", "9876543210", "India", "John@123"),
				new Person(02, "Sophie","sophie@gmail.com", "30","female", "8765432109", "USA", "Sophie@123")).toList();
		List<Person> saved=repo.saveAll(persons);

		List<Person> existingPerson=restTemplate.getForObject(baseUrl, List.class);
		System.out.println(existingPerson);
		assertNotNull(existingPerson);
		assertEquals(saved.size(), repo.findAll().size());

	}

	@Test
	public void updateRegisterTest()
	{
		baseUrl = baseUrl + ":" + port + "/updatepersonTest/{id}";
		Person per1= new Person(01,"John","john@gmail.com","28","male","9876543210","India", "John@123");
		Person saved=repo.save(per1);

		Person per2= new Person(01,"Sophie","sophie@gmail.com", "30","female", "8765432109", "USA", "Sophie@123");

		restTemplate.put(baseUrl, per2, per1.getId());
		Person updated=repo.findById(per1.getId()).get();
		System.out.println(updated);
		assertNotNull(updated);
		assertEquals(per2.getName(),updated.getName());

	}

	@Test
	public void deleteRegisterByIdTest()
	{
		baseUrl = baseUrl + ":" + port + "/deletepersonTest/{id}";
		Person per1= new Person(01,"John","john@gmail.com","28","male","9876543210","India", "John@123");
		Person saved=repo.save(per1);

		restTemplate.delete(baseUrl, saved.getId());
		assertEquals(0,repo.findAll().size());
	}

	@Test
	public void deleteRegistersTest()
	{
		baseUrl = baseUrl + ":" + port + "/deletepersonsTest";
		List<Person> persons= Stream.of(new Person(01, "John","john@gmail.com", "28","male", "9876543210", "India", "John@123"),
				new Person(02, "Sophie","sophie@gmail.com", "30","female", "8765432109", "USA", "Sophie@123")).toList();
		List<Person> saved=repo.saveAll(persons);

		restTemplate.delete(baseUrl);
		assertEquals(0,repo.findAll().size());
	}

}
