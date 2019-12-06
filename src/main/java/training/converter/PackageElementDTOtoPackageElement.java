package training.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.PackageElementDTO;
import training.model.PackageElement;

@Component
public class PackageElementDTOtoPackageElement implements Converter<PackageElementDTO, PackageElement> {

	@Override
	public PackageElement convert(PackageElementDTO source) {

		if(source == null) {
			return null;
		}
		
		PackageElement packageElement = new PackageElement();
		packageElement.setId(source.getId());
		packageElement.setName(source.getName());
		packageElement.setDescription(source.getDescription());
		return packageElement;
	}

}
