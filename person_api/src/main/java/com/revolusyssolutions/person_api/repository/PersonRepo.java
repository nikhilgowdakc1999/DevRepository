package com.revolusyssolutions.person_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revolusyssolutions.person_api.entities.Person;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {

	public Optional<Person> findByemail(String email);
	
}
