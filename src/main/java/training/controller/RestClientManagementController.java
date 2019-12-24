package training.controller;

import java.util.List;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import training.converter.ClientPackageDTOtoClientPackage;
import training.dto.ClientPackageDTO;
import training.dto.MultipleExercisetoRoundDTO;
import training.model.ClientPackage;
import training.model.ClientPackageElement;
import training.model.Package;
import training.model.ElementsInPackages;
import training.service.ClientPackageElementService;
import training.service.ClientPackageService;
import training.service.PackageService;

@RestController
public class RestClientManagementController {

	@Autowired
	ClientPackageDTOtoClientPackage clientPackageDTOtoClientPackage;
	
	@Autowired
	ClientPackageService clientPackageService;
	
	@Autowired
	ClientPackageElementService clientPackageElementService;
	
	@Autowired
	PackageService packageService;
	
	@PostMapping(value = { "/addPackageToClient" })
	public ResponseEntity<?> addPackageToClientPackage(@Valid @RequestBody ClientPackageDTO clientPackageDTO) {
		System.out.println("Client id : " + clientPackageDTO.getClientId());
		System.out.println("Package id : " + clientPackageDTO.getPackageId());
		ClientPackage clientPackage = clientPackageDTOtoClientPackage.convert(clientPackageDTO);
		clientPackage.setClientPackageStatus(true);
		clientPackageService.save(clientPackage);
		
		List<ElementsInPackages> elementsInPackagesList = packageService.findOne(clientPackageDTO.getPackageId()).getElementsInPackages();
		for(ElementsInPackages elementsInPackages : elementsInPackagesList) {
			ClientPackageElement clientPackageElement = new ClientPackageElement();
			clientPackageElement.setClientPackage(clientPackage);
			clientPackageElement.setCounter(Integer.parseInt(elementsInPackages.getNumber()+""));
			clientPackageElement.setElementsInPackages(elementsInPackages);
			clientPackageElement.setClientPackageElementStatus(true);
			
			clientPackage.addClientPackageElements(clientPackageElement);
			
			clientPackageElementService.save(clientPackageElement);
			clientPackageService.save(clientPackage);
//			clientPackageElement.se
		}
			
		JSONObject obj = new JSONObject();
		return ResponseEntity.ok(obj.toString());
	}
}
