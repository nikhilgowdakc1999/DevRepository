package com.revolusyssolutions.person_api.entities.services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.revolusyssolutions.person_api.advice.ResourceAlreadyPresentException;
import com.revolusyssolutions.person_api.advice.ResourceNotFoundException;
import com.revolusyssolutions.person_api.entities.Login;
import com.revolusyssolutions.person_api.entities.Person;
import com.revolusyssolutions.person_api.repository.PersonRepo;

@Service
public class PersonServiceImpl implements PersonService{

	
	@Autowired
	private PersonRepo repo;
	
	@Autowired
	EmailService emailService;
	
	String generatedOtp;

	@Override
	public List<Person> getAll()
	{
		List<Person> persons=this.repo.findAll();
		System.out.println("All Persons list :"+persons);
		return persons;
	}

	@Override
	public Optional<Person> getPerson(int id)
	{
		Optional<Person> person=this.repo.findById(id);
		System.out.println("Person with id : "+id+" :"+person);
		return person;
	}

	@Override
	public Person addPerson(Person person) {
		Optional<Person> storedvalue=repo.findByemail(person.getEmail());
		if(storedvalue.isPresent())throw new ResourceAlreadyPresentException("Record already present!!");
		repo.save(person);
		System.out.println("Person added :"+person);
		return person;
	}
	
	@Override
	public String OTPGeneration(Login log)  //imp
	{
		Optional<Person> existingUser = this.repo.findByemail(log.getEmail());
		if(existingUser.isPresent())
		{
			Random r = new Random();
			generatedOtp = String.format("%06d", r.nextInt(100000)); // generates random nos in b/n 0-99999
            // % -> start of format specifier,0-> 'O'padding on left,6-> width of characters,d-> conversion character for a Decimal Integer
			String subject = "Email Verfication";
			String body = "Your verification OTP is "+generatedOtp;
			//Email Send
			this.emailService.sendEmail(existingUser.get().getEmail(), subject, body);

			return "Generated Otp : "+generatedOtp;
		}else
		{
			return "User doesn't exist for provided mail!!";
		}

	}

	@Override
	public Person updatePerson(Person person, int id)
	{	
		Optional<Person> existingrecord=repo.findById(id);
		if(existingrecord.isPresent())
		{
			existingrecord.get().setAge(person.getAge());
			existingrecord.get().setCountry(person.getCountry());
			existingrecord.get().setEmail(person.getEmail());
			existingrecord.get().setGender(person.getGender());
			existingrecord.get().setId(id);
			existingrecord.get().setName(person.getName());
			existingrecord.get().setPhoneno(person.getPhoneno());
			existingrecord.get().setPassword(person.getPassword());
			System.out.println("Person updated :"+existingrecord.get());
			return repo.save(existingrecord.get());
		}
		else
		{
			throw new ResourceNotFoundException("Person data Not Found with id: "+id);
		}	
	}

	@Override
	public Person updatedata(Map<String,Object> map, int id)
	{
		Optional<Person> existingrecord=repo.findById(id);
		if(existingrecord.isPresent())
		{
			map.forEach((key,value)->{
				Field field=ReflectionUtils.findField(Person.class, key); //simple utility class for working with reflection apis
				field.setAccessible(true); // gives access to modify
				ReflectionUtils.setField(field, existingrecord.get(), value);
			});
			System.out.println("Person data updated :"+existingrecord.get());
			return repo.save(existingrecord.get());
		}
		else
		{
			throw new ResourceNotFoundException("Person data Not Found with id: "+id);
		}
	}

	@Override
	public void deletePerson(int id)
	{
		boolean value=repo.existsById(id);
		if(value==true)
		{
			repo.deleteById(id);
			System.out.println("person deleted :"+id);
		}
		else
		{
			throw new ResourceNotFoundException("Person data Not Found with id: "+id);
		}
	}

	@Override
	public void deleteAllPersons()
	{
		repo.deleteAll();	
		generatedOtp=null;
		System.out.println("All Persons deleted");
	}

	@Override
	public String RegisterLogin(Login log,String otp) {
		Optional<Person> existingRecord=repo.findByemail(log.getEmail());
		if(existingRecord.isPresent()) 
		{
			if(otp.equals(generatedOtp) && log.getPassword().equals(existingRecord.get().getPassword()))
			{
				return "Login Succesfull!!";
			}else
			{
				throw new ResourceNotFoundException("Password incorrect or OTP invalid!!");
			}	
		}else
		{
			throw new ResourceNotFoundException("User doesn't exist with provided email!!");
		}
	}

	@Override
	public String updatePassword(Login log,String otp)
	{
		Optional<Person> existingperson=repo.findByemail(log.getEmail());
		if(existingperson.isPresent()) 
		{
			if(generatedOtp.equals(otp))
			{
				existingperson.get().setPassword(log.getPassword());
				Person per=repo.save(existingperson.get());
				return "Password changed succesfully";
			}
			else
			{
				throw new ResourceNotFoundException("Invalid OTP provided!!");
			}
			
		}
		else 
		{
			throw new ResourceNotFoundException("User doesn't exist with provided email!!");
		}	
	}
	
}
