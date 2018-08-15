package training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	List<Client> findByNameIgnoreCaseContainingAndFamilyNameIgnoreCaseContaining(String name, String familyName);

}
