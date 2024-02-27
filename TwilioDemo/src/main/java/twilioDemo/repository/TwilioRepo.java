package twilioDemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import twilioDemo.entity.TwilioEntity;

@Repository
public interface TwilioRepo extends JpaRepository<TwilioEntity, Integer>{

	public Optional<TwilioEntity> findByname(String name);
}
