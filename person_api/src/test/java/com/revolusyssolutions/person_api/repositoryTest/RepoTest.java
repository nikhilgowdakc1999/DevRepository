package com.revolusyssolutions.person_api.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.revolusyssolutions.person_api.entities.Person;
import com.revolusyssolutions.person_api.repository.PersonRepo;

@DataJpaTest //designed particularly for testing JPA Repositories and its interaction with underlying database & tests are transactional and rollback at end of each test i.e once test is completed
public class RepoTest {   //transactional means non-committed statements

	@Autowired
	private PersonRepo repository;

	@BeforeEach  //runs before execution of each test case and common logic which is shared by each testcases is written here 
	public void setup()
	{
		Person per=new Person( 1, "John","john@gmail.com", "28","male", "9876543210", "India", "John@123");
		repository.save(per);
		System.out.println("record created!!");
	}

	@Test
	public void testFindByEmail()
	{	
		Optional<Person> existing=repository.findByemail("john@gmail.com");
		System.out.println(existing);
		System.out.println("success");
		assertTrue(existing.isPresent());
		assertEquals("john@gmail.com",existing.get().getEmail());
	}

	@Test
	public void testFindByEmail_NotFound()
	
	{			
		Optional<Person> existing=repository.findByemail("sophie@gmail.com");
		System.out.println("failure");
		assertFalse(existing.isPresent());
	}

	@AfterEach //runs after execution of each test case and common logic which is shared by each testcases is written here 
	public void tearDown()
	{
		repository.deleteAll();
		System.out.println("record deleted!!");
		Person per=null;
	}
}
