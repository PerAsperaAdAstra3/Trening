package training.controller;

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

@RestController
public class RestClientManagementController {

	@Autowired
	ClientPackageDTOtoClientPackage clientPackageDTOtoClientPackage;
	
	@PostMapping(value = { "/addPackageToClient" })
	public ResponseEntity<?> addPackageToClientPackage(@Valid @RequestBody ClientPackageDTO clientPackageDTO) {
		System.out.println("Client id : " + clientPackageDTO.getClientId());
		System.out.println("Package id : " + clientPackageDTO.getPackageId());
		ClientPackage clientPackage = clientPackageDTOtoClientPackage.convert(clientPackageDTO);
		JSONObject obj = new JSONObject();
		return ResponseEntity.ok(obj.toString());
	}
}
