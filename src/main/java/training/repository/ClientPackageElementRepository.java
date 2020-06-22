package training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.ClientPackage;
import training.model.ClientPackageElement;

@Repository
public interface ClientPackageElementRepository extends JpaRepository<ClientPackageElement, Long> {
	List<ClientPackageElement> findByClientPackageIn(List<ClientPackage> clientPackage);
}
