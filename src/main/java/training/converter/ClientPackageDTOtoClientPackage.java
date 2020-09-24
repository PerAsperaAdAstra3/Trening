package training.converter;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ClientPackageDTO;
import training.enumerations.ClientPackageStateEnum;
import training.model.ClientPackage;
import training.model.Package;
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
		clientPackage.setClientPackagePrice(source.getPriceOfClientPackage());
		
		clientPackage.setPayed(source.getPayed());
		String purchaseDateString = "";
		Date purchaseDateGenerated = null;
		if(source.getPurchaseDate() != null) {
			purchaseDateString = source.getPurchaseDate();
			String[] arrayString = purchaseDateString.split("-");
			purchaseDateGenerated = new Date(arrayString[2]+"-"+arrayString[1]+"-"+arrayString[0]);
			System.out.println(purchaseDateGenerated);
		}
		clientPackage.setPurchaseDate(purchaseDateGenerated);
		
		if(source.getClientPackageActive().equals(ClientPackageStateEnum.ACTIVE.getNameText())) {
			clientPackage.setClientPackageActive(ClientPackageStateEnum.ACTIVE);
		} else {
			clientPackage.setClientPackageActive(ClientPackageStateEnum.NOTACTIVE);
		}
	
		return clientPackage;
	}

}
