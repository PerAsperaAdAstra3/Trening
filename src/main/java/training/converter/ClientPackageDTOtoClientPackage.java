package training.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ClientPackageDTO;
import training.model.ClientPackage;
import training.model.ClientPackageElement;
import training.model.Package;
import training.model.PackageElement;
import training.service.ClientService;
import training.service.PackageService;

@Component
public class ClientPackageDTOtoClientPackage implements Converter<ClientPackageDTO, ClientPackage> {

	@Autowired
	ClientService clientService;
	
	@Autowired
	PackageService packageService;
	
	@Override
	public ClientPackage convert(ClientPackageDTO source) {

		if (source == null) {
			return null;
		}
		Package packageUnit = packageService.findOne(source.getPackageId());
		ClientPackage clientPackage = new ClientPackage();
		clientPackage.setId(source.getId());
		clientPackage.setClient(clientService.findOne(source.getClientId()));
		clientPackage.setPackageUnit(packageUnit);
		for(PackageElement packageElement : packageUnit.getElementsInPackages()) {
			clientPackage.addClientPackageElements(clientPackageElement);
		}
		return clientPackage;
	}

}
