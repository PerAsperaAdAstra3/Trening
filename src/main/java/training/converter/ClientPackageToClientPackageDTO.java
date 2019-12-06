package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ClientPackageDTO;
import training.model.ClientPackage;

@Component
public class ClientPackageToClientPackageDTO implements Converter<ClientPackage, ClientPackageDTO> {

	@Override
	public ClientPackageDTO convert(ClientPackage source) {
		
		if(source == null){
			return null;
		}
		
		ModelMapper modelMapper = new ModelMapper();
		ClientPackageDTO clientPackageDTO = modelMapper.map(source, ClientPackageDTO.class);
		return clientPackageDTO;
	}

	public List<ClientPackageDTO> convert(List<ClientPackage> source){
		List<ClientPackageDTO> clientPackageDTO = new ArrayList<ClientPackageDTO>();
		
		for(ClientPackage clientPackage : source){
			clientPackageDTO.add(convert(clientPackage));
		}
		return clientPackageDTO;
	}
	
}
