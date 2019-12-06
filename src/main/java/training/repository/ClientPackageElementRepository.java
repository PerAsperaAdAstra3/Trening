package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.ClientPackageElement;

@Repository
public interface ClientPackageElementRepository extends JpaRepository<ClientPackageElement, Long> {

}
