package training.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.PackageDTO;
import training.model.Package;

@Component
public class PackageDTOtoPackage implements Converter<PackageDTO, Package> {

	@Override
	public Package convert(PackageDTO source) {
		
		if(source == null) {
			return null;
		}
		
		Package packageUnit = new Package();
		packageUnit.setId(source.getId());
		packageUnit.setNameOfPackage(source.getNameOfPackage());
		
		return packageUnit;
	}

}
