package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ClientDTO;
import training.model.Client;

@Component
public class ClientToClientDTO implements Converter<Client,ClientDTO> {

	@Override
	public ClientDTO convert(Client source) {
		
		if(source == null){
			return null;
		}
		
		ModelMapper modelMapper = new ModelMapper();
		ClientDTO clientDTO = modelMapper.map(source, ClientDTO.class);
		return clientDTO;
	}
	
	public List<ClientDTO> convert(List<Client> source){
		List<ClientDTO> clientDTO = new ArrayList<ClientDTO>();
		
		for(Client client : source){
			clientDTO.add(convert(client));
		}
		return clientDTO;
	}

}
