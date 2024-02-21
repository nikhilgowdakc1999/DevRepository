package com.revolusys.Registration_api.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revolusys.Registration_api.entities.Register;
import com.revolusys.Registration_api.repository.RegisterRepo;
import com.revolusys.Registration_api.service.RegisterService;

@SpringBootTest
public class ServiceTest {

	@Autowired 
	private RegisterService regservice;

	@MockBean
	private RegisterRepo repository;

	@Test
	void contextLoads()
	{
		System.out.println("Unit Testing");
	}

	@Test
	public void testAdd()
	{
		Register register= new Register(01,"John","john@gmail.com","28","male","9876543210","India", "John@123");
		when(repository.save(any(Register.class))).thenReturn(register);
		assertEquals(register,regservice.add(register));
	}


//	@Test
	public void testgetAll()
	{
		when(repository.findAll()).thenReturn(Stream.of(new Register(01,"John","john@gmail.com","28","male","9876543210","India", "John@123"),
				new Register(02,"Sophie","sophie@gmail.com","29","female","8765432109","USA", "Sophie@123")).toList());
		assertEquals(2,regservice.getAll().size());
	}


//	@Test
	public void testupdate()
	{
		int id=1;
		Map<String,Object> map=new HashMap<>();
		map.put("name", "Sophie");
		map.put("age","29");
		map.put("gender","male");
		when(repository.findById(id)).thenReturn(Optional.of(new Register()));
		Register updatedRegister=regservice.update(map, id);
		verify(repository,times(1)).save(any(Register.class));
	}


	@Test
	public void testDeleteAll()
	{
	    (repository).deleteAll();        
		verify(repository, times(1)).deleteAll();
	}
	
}
