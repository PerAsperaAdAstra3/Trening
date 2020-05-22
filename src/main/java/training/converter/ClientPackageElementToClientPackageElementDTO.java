package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ClientPackageElementDTO;
import training.enumerations.ClientPackageStateEnum;
import training.model.ClientPackageElement;

@Component
public class ClientPackageElementToClientPackageElementDTO implements Converter<ClientPackageElement, ClientPackageElementDTO> {

	@Override
	public ClientPackageElementDTO convert(ClientPackageElement source) {

		if(source == null)
			return null;
		
		ModelMapper modelMapper = new ModelMapper();
		ClientPackageElementDTO clientPackageElementDTO = modelMapper.map(source, ClientPackageElementDTO.class);
		if(source.isClientPackageElementStatus()) {
			clientPackageElementDTO.setClientPackageElementStatus(ClientPackageStateEnum.ACTIVE.getNameText());
		} else {
			clientPackageElementDTO.setClientPackageElementStatus(ClientPackageStateEnum.NOTACTIVE.getNameText());			
		}
		clientPackageElementDTO.setDescription(source.getElementsInPackages().getPackageElementEIP().getDescription());
		clientPackageElementDTO.setName(source.getElementsInPackages().getPackageElementEIP().getPackageElementName());
		clientPackageElementDTO.setCount(source.getCounter());
		clientPackageElementDTO.setActiveLeft(source.getActiveLeft());
		return clientPackageElementDTO;
	}

	public List<ClientPackageElementDTO> convert(List<ClientPackageElement> source ){
		List<ClientPackageElementDTO> clientPackageElementDTOList = new ArrayList<ClientPackageElementDTO>();
		
		for(ClientPackageElement clientPackageElement : source){
			clientPackageElementDTOList.add(convert(clientPackageElement));
		}	
		return clientPackageElementDTOList;
	}
	
}
