package training.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import training.converter.ClientPackageDTOtoClientPackage;
import training.converter.ClientPackageElementToClientPackageElementDTO;
import training.converter.ClientPackageToClientPackageDTO;
import training.dto.ClientPackageDTO;
import training.dto.ClientPackageElementDTO;
import training.model.ClientPackage;
import training.model.ClientPackageElement;
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
	
	@Autowired
	ClientPackageToClientPackageDTO clientPackageToClientPackageDTO;
	
	@Autowired
	ClientPackageElementToClientPackageElementDTO clientPackageElementToClientPackageElementDTO;
	
	@PostMapping(value = { "/addPackageToClient" })
	public ResponseEntity<?> addPackageToClientPackage(@Valid @RequestBody ClientPackageDTO clientPackageDTO) {
		JSONObject obj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		clientPackageDTO.setClientPackageStatus("Aktivan");
		ClientPackage clientPackage = clientPackageDTOtoClientPackage.convert(clientPackageDTO);
		clientPackageService.save(clientPackage);
		List<ClientPackageElement> clientPackageElementsList = new ArrayList<ClientPackageElement>();
		List<ElementsInPackages> elementsInPackagesList = packageService.findOne(clientPackageDTO.getPackageId()).getElementsInPackages();
		for(ElementsInPackages elementsInPackages : elementsInPackagesList) {
			ClientPackageElement clientPackageElement = new ClientPackageElement();
			clientPackageElement.setClientPackage(clientPackage);
			clientPackageElement.setCounter(Integer.parseInt(elementsInPackages.getNumber()+""));
			clientPackageElement.setActiveLeft(Integer.parseInt(elementsInPackages.getNumber()+""));
			clientPackageElement.setElementsInPackages(elementsInPackages);
			clientPackageElement.setClientPackageElementStatus(true);
			
			clientPackage.addClientPackageElements(clientPackageElement);
			
			clientPackageElementService.save(clientPackageElement);
			clientPackageService.save(clientPackage);
			clientPackageElementsList.add(clientPackageElement);
		}
			

			if(clientPackage.getClientPackagePrice() == null) {
				clientPackage.setClientPackagePrice(0l);
			}

		
		//obj.put("clientPackage", jSONWriter.valueToString(clientPackageToClientPackageDTO.convert(clientPackage)));//clientPackageToClientPackageDTO.convert(clientPackage));
		JSONObject clientPackageJSON = new JSONObject(clientPackageToClientPackageDTO.convert(clientPackage));
		
		JSONObject clientPackageElementsJSON = new JSONObject(clientPackageElementToClientPackageElementDTO.convert(clientPackageElementsList));

		List<ClientPackageElementDTO> clientPackageElementsDTOList = clientPackageElementToClientPackageElementDTO.convert(clientPackageElementsList);
		
		for(int i=0; i < clientPackageElementsDTOList.size(); i++) {
			JSONObject clientPackageElementJSON = new JSONObject(clientPackageElementsDTOList.get(i));
			jsonArray.put(i, clientPackageElementJSON);
		}
		
		obj.put("clientPackageJSON", clientPackageJSON);
		obj.put("clientPackageElementsJSON", jsonArray);

		return ResponseEntity.ok(obj.toString());
	}
	
	@PostMapping(value = { "/useUpAPackageElement" })
	public ResponseEntity<?> useUpAPackageElement(@Valid @RequestBody ClientPackageElementDTO clientPackageElementDTO) {
		JSONObject obj = new JSONObject();	
		ClientPackageElement clientPackageElement = clientPackageElementService.findOne(clientPackageElementDTO.getId()) ;
		if(clientPackageElement.getActiveLeft() > 0) {
			clientPackageElement.setActiveLeft(clientPackageElement.getActiveLeft() - 1);
		}
		
		if(clientPackageElement.getActiveLeft() < 1) {
			clientPackageElement.setClientPackageElementStatus(false);
		}
		
		clientPackageElementService.save(clientPackageElement);
		
		ClientPackage clientPackage = clientPackageElement.getClientPackage();
		
		clientPackage.setClientPackageStatus(false);
		for(ClientPackageElement clientPackageElementX : clientPackage.getClientPackageElements()) {
			if(clientPackageElementX.isClientPackageElementStatus()) {
				clientPackage.setClientPackageStatus(true);
			}
		}
		
		clientPackageService.save(clientPackage);
		
		obj.put("activeLeft", clientPackageElement.getActiveLeft());
		obj.put("clientPackageElementId", clientPackageElement.getId());
		
		obj.put("clientPackageId", clientPackage.getId());
		obj.put("clientPackagePayed", clientPackage.isPayed());
		
		obj.put("clientPackageStatus", clientPackage.isClientPackageStatus());
		if(clientPackage.isClientPackageStatus()) {
			obj.put("clientPackageStatus", "Aktivan");
		} else {
			obj.put("clientPackageStatus", "Neaktivan");
		}
		
		if(clientPackageElement.isClientPackageElementStatus()) {
			obj.put("clientPackageElementState", "Aktivan");
		} else {
			obj.put("clientPackageElementState", "Neaktivan");
		}
		return ResponseEntity.ok(obj.toString());
	}
	
	///deleteClientPackage
	
	@PostMapping(value = { "/deleteClientPackage" })
	public ResponseEntity<?> deleteClientPackage(@Valid @RequestBody ClientPackageDTO clientPackageDTO) {
		JSONObject obj = new JSONObject();	
		ClientPackage clientPackage = clientPackageService.delete(clientPackageDTO.getId()) ;

		return ResponseEntity.ok(obj.toString());
	}
	
	@PostMapping(value = { "/changeClientPackageStatus" })
	public ResponseEntity<?> changeClientPackageStatus(@Valid @RequestBody ClientPackageDTO clientPackageDTO) {
		JSONObject obj = new JSONObject();	

		System.out.println("Client package ID : " + clientPackageDTO.getId());
		
		ClientPackage clientPackage = clientPackageService.findOne(clientPackageDTO.getId()) ;

		if(clientPackage.isPayed()) {
			clientPackage.setPayed(false);
		} else {
			clientPackage.setPayed(true);
		}

		clientPackageService.save(clientPackage);
		
		obj.put("paymentStatus", clientPackage.isPayed());
		
		return ResponseEntity.ok(obj.toString());
	}
}
