package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.ClientPackage;

@Repository
public interface ClientPackageRepository extends JpaRepository<ClientPackage, Long> {

}
