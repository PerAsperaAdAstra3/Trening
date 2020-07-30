package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.PackageElementDTO;
import training.model.PackageElement;

@Component
public class PackageElementToPackageElementDTO implements Converter<PackageElement, PackageElementDTO> {

	@Override
	public PackageElementDTO convert(PackageElement source) {
		
		if(source == null) {
			return null;
		}
		
		ModelMapper modelMapper = new ModelMapper();
		PackageElementDTO packageElementDTO = modelMapper.map(source, PackageElementDTO.class);
		System.out.println("*******************");
		System.out.println("Name : "+packageElementDTO.getPackageElementName());
		System.out.println("Description : "+packageElementDTO.getDescription());
		return packageElementDTO;
	}

	public List<PackageElementDTO> convert(List<PackageElement> sources) {	
		List<PackageElementDTO> packageElementDTOList = new ArrayList<PackageElementDTO>();
		
		for(PackageElement packageElement : sources) {
			packageElementDTOList.add(convert(packageElement));
		}
		
		return packageElementDTOList;
	}
}
