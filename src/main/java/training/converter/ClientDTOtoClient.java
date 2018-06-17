package training.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ClientDTO;
import training.model.Client;

@Component
public class ClientDTOtoClient implements Converter<ClientDTO, Client> {

	@Override
	public Client convert(ClientDTO source) {

		if (source == null) {
			return null;
		}

		Client client = new Client();

		client.setName(source.getName());
		client.setFamilyName(source.getFamilyName());
		return client;
	}
}