package training.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ElementsInPackagesDTO;
import training.model.ElementsInPackages;
import training.service.ElementsInPackagesService;
import training.service.PackageElementService;
import training.service.PackageService;

@Component
public class ElementsInPackagesDTOtoElementsInPackages implements Converter<ElementsInPackagesDTO,ElementsInPackages> {

	@Autowired
	PackageService packageService;
	
	@Autowired
	PackageElementService packageElementService;
	
	@Override
	public ElementsInPackages convert(ElementsInPackagesDTO source) {

		if(source == null) {
			return null;
		}

		ElementsInPackages elementsInPackages = new ElementsInPackages();
		elementsInPackages.setElemInPackagesId(source.getElemInPackagesId());
		elementsInPackages.setPackage(packageService.findOne(source.getPackageId()));
		elementsInPackages.setNumber(source.getNumber());
		elementsInPackages.setPackageElementEIP(packageElementService.findOne(source.getPackageElementId()));
		return elementsInPackages;
	}

}
