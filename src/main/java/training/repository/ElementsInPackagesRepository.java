package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.ElementsInPackages;
import training.model.PackageElement;
import training.model.Package;

@Repository
public interface ElementsInPackagesRepository extends JpaRepository<ElementsInPackages, Long> {
	ElementsInPackages findByPackage1AndPackageElementEIP(Package package1, PackageElement packageElement);
	
	//findByPackage1AndPackageElement(Package package1, PackageElement packageElement);
}
