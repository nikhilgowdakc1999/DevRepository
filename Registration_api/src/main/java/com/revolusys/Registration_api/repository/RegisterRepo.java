package com.revolusys.Registration_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revolusys.Registration_api.entities.Register;

@Repository
public interface RegisterRepo extends JpaRepository<Register, Integer> {

	public Optional<Register> findByemail(String email);
}
