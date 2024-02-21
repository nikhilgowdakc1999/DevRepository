package com.revolusys.Registration_api.repositoryTest;

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
import org.springframework.boot.test.context.SpringBootTest;

import com.revolusys.Registration_api.entities.Register;
import com.revolusys.Registration_api.repository.RegisterRepo;

@DataJpaTest //focuses only on Jpa components and uses embedded in-memory database h2
public class RepositoryTest {

	@Autowired
	private RegisterRepo regRepo;
	
	@BeforeEach
	public void setup()
	{
		Register register= new Register(01,"John","john@gmail.com","28","male","9876543210","India", "John@123");
		Register reg =regRepo.save(register);
		System.out.println("Record creadted!!");
	}
	
	@AfterEach
	public void tearDown()
	{
		regRepo.deleteAll();
		System.out.println("Record Deleted!!");
		Register register=null;
	}
	
	@Test
	public void testFindByEmail()
	{	
		Optional<Register> existing=regRepo.findByemail("john@gmail.com");
		assertTrue(existing.isPresent());
		assertEquals("john@gmail.com",existing.get().getEmail());	
	}

	@Test
	public void testFindByEmail_NotFound()
	{			
		Optional<Register> existing=regRepo.findByemail("sophie@gmail.com");
		assertFalse(existing.isPresent());
	}
}
