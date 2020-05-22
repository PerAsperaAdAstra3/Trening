package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.ElementsInPackages;
import training.model.PackageElement;
import training.model.Package;

@Repository
public interface ElementsInPackagesRepository extends JpaRepository<ElementsInPackages, Long> {
	ElementsInPackages findByElementsPackageAndPackageElementEIP(Package elementsPackage, PackageElement packageElement);
}
