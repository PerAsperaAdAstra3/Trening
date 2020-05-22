package training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.Client;
import training.model.ClientPackage;

@Repository
public interface ClientPackageRepository extends JpaRepository<ClientPackage, Long> {
	List<ClientPackage> findByClient(Client client);
}
