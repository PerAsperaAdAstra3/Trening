package training.converter;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ClientDTO;
import training.model.Client;

@Component
public class ClientToClientDTO implements Converter<Client,ClientDTO> {

	@Override
	public ClientDTO convert(Client source) {
		
		if(source == null) {
			return null;
		}
		
		ModelMapper modelMapper = new ModelMapper();
		ClientDTO clientDTO = modelMapper.map(source, ClientDTO.class );
		return clientDTO;
	}

}
