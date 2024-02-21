package otpVerificationDemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import otpVerificationDemo.entity.UserData;

@Repository
public interface UserRepository extends JpaRepository<UserData, Integer>
{
	public Optional<UserData> findByemail(String email);
}
