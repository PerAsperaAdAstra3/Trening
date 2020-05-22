package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.PackageDTO;
import training.model.Package;

@Component
public class PackageToPackageDTO implements Converter<Package, PackageDTO> {

	@Override
	public PackageDTO convert(Package source) {

		if(source == null) {
			return null;
		}
		
		ModelMapper modelMaper = new ModelMapper();
		PackageDTO packageDTO = modelMaper.map(source, PackageDTO.class);
		return packageDTO;
	}

	public List<PackageDTO> convert(List<Package> sources) {
		List<PackageDTO> packageDTOList = new ArrayList<PackageDTO>();
		
		for(Package source : sources) {
			packageDTOList.add(convert(source));
		}
		return packageDTOList;
	}
	
}
