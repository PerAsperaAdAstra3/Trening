package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.Package;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long>{

}
