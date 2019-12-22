package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ElementsInPackagesDTO;
import training.dto.PackageElementDTO;
import training.model.ElementsInPackages;
import training.model.PackageElement;
import training.service.PackageElementService;

@Component
public class ElementsInPackagesToElementsInPackagesDTO implements Converter<ElementsInPackages,ElementsInPackagesDTO> {

	@Autowired
	PackageElementService packageElementService;
	
	@Override
	public ElementsInPackagesDTO convert(ElementsInPackages source) {
		if(source == null) {
			return null;
		}
		
		ElementsInPackagesDTO elementsInPackagesDTO = new ElementsInPackagesDTO();
		
		elementsInPackagesDTO.setElemInPackagesId(source.getElemInPackagesId());
		elementsInPackagesDTO.setPackageElementDescription(source.getPackageElementEIP().getDescription());
		elementsInPackagesDTO.setPackageElementName(source.getPackageElementEIP().getName());
		elementsInPackagesDTO.setNumber(source.getNumber());
		elementsInPackagesDTO.setPackageElementId(source.getPackageElementEIP().getPackageElementID());
		elementsInPackagesDTO.setPackageId(source.getPackage().getId());
		
		return elementsInPackagesDTO;
	}
	
	public List<ElementsInPackagesDTO> convert(List<ElementsInPackages> sources) {	
		List<ElementsInPackagesDTO> elementsInPackagesDTOList = new ArrayList<ElementsInPackagesDTO>();
		
		for(ElementsInPackages elementsInPackage : sources) {
			elementsInPackagesDTOList.add(convert(elementsInPackage));
		}
		
		return elementsInPackagesDTOList;
	}
}
