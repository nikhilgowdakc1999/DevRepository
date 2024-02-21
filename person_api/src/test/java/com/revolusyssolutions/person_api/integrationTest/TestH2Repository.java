package com.revolusyssolutions.person_api.integrationTest;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revolusyssolutions.person_api.entities.Person;

public interface TestH2Repository extends JpaRepository<Person, Integer> {

}
