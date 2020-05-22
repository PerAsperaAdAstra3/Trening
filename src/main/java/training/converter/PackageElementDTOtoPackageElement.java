package training.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.PackageElementDTO;
import training.model.PackageElement;
import training.service.PackageService;

@Component
public class PackageElementDTOtoPackageElement implements Converter<PackageElementDTO, PackageElement> {

	@Autowired
	PackageService PackageService;
	
	@Override
	public PackageElement convert(PackageElementDTO source) {

		if(source == null) {
			return null;
		}
		
		PackageElement packageElement = new PackageElement();
		packageElement.setPackageElementID(source.getPackageElementID());
		packageElement.setPackageElementName(source.getPackageElementName());
		packageElement.setDescription(source.getDescription());
		return packageElement;
	}

}
