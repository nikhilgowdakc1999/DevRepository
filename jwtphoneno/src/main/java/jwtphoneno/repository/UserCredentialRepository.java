package jwtphoneno.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jwtphoneno.entity.UserCredential;
import java.util.List;


@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {

	public Optional<UserCredential>  findByPhoneno(String phoneno);
	
	public Optional<UserCredential> findByEmail(String email); 
	
	public Optional<UserCredential> findByName(String username); 
}
