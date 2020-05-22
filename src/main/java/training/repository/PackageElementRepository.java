package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.PackageElement;

@Repository
public interface PackageElementRepository extends JpaRepository<PackageElement, Long>{

}
