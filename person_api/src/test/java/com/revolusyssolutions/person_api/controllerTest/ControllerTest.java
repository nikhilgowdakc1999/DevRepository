package com.revolusyssolutions.person_api.controllerTest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolusyssolutions.person_api.controller.TestPersonController;
import com.revolusyssolutions.person_api.entities.Person;
import com.revolusyssolutions.person_api.entities.services.PersonService;

@WebMvcTest(controllers = TestPersonController.class)
public class ControllerTest {

	@MockBean
	private PersonService service; 
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
//	@Test
	public void createRecordTest() throws Exception
	{
		Person person= new Person(01,"John","john@gmail.com","28","male","9876543210","India", "John@123");
		when(service.addPerson(person)).thenReturn(person);
		
		this.mockMvc.perform(post("/savepersonTest")
				   .contentType(MediaType.APPLICATION_JSON)
				   .content(objectMapper.writeValueAsString(person)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", is(person.getName())))
	    .andExpect(jsonPath("$.gender", is(person.getGender())))
		.andExpect(jsonPath("$.age", is(person.getAge())));
		
	}
	
	@Test
	public void fetchRecordById() throws Exception
	{
        Person per=new Person(02, "Sophie","sophie@gmail.com", "30","female", "8765432109", "USA", "Sophie@123");
		when(service.getPerson(anyInt())).thenReturn(Optional.of(per));
		
		this.mockMvc.perform(get("/getperson/{id}", per.getId()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", is(per.getName())))
		.andExpect(jsonPath("$.age", is(per.getAge())));
	}
		
}
