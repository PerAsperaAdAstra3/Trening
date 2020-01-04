package training.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ClientPackageElementDTO;
import training.model.ClientPackageElement;
import training.service.ClientPackageService;
import training.service.ElementsInPackagesService;

@Component
public class ClientPackageElementDTOtoClientPackageElement implements Converter<ClientPackageElementDTO,ClientPackageElement> {

	@Autowired
	ClientPackageService clientPackageService;
	
	@Autowired
	ElementsInPackagesService elementsInPackagesService;
	
	@Override
	public ClientPackageElement convert(ClientPackageElementDTO source) {
		
		if (source == null) {
			return null;
		}
		
		ClientPackageElement clientPackageElement = new ClientPackageElement();
		clientPackageElement.setClientPackage(clientPackageService.findOne(source.getClientPackageId()));
		
		if(source.getClientPackageElementStatus().equals("Aktivan")) {
			clientPackageElement.setClientPackageElementStatus(true);
		} else {
			clientPackageElement.setClientPackageElementStatus(false);
		}
		
		clientPackageElement.setCounter(source.getCount());
		clientPackageElement.setElementsInPackages(elementsInPackagesService.findOne(source.getElementsInPackagesId()));
		clientPackageElement.setId(source.getId());

		return null;
	}

}
