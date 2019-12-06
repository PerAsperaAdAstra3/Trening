package training.controller;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import training.dto.ExerciseInRoundDTO;
import training.dto.MultipleExercisetoRoundDTO;
import training.dto.PackageDTO;
import training.dto.PackageDTOAjax;
import training.dto.PackageElementDTO;
import training.model.ExerciseInRound;
import training.model.Training;
import training.model.Package;
import training.model.PackageElement;
import training.service.PackageElementService;
import training.service.PackageService;

@RestController
public class RestPackageController {

	@Autowired
	PackageService packageService;

	@Autowired
	PackageElementService packageElementService;

	@PostMapping(value = { "/addPackagePackageElement"})
	public ResponseEntity<?> addPackagePackageElement(@Valid @RequestBody PackageDTOAjax packageDTOAjax) {
		JSONObject obj = new JSONObject();
		Package packageUnit = packageService.findOne(packageDTOAjax.getId());
		Long trainingId = -1l;

		PackageElement packageElement = packageElementService.findeOne(packageDTOAjax.getPackageElementId());
		packageUnit.addPackageElements(packageElement);

		System.out.println("Package element name : " + packageElement.getName());
		
		System.out.println("Package element description : " + packageElement.getDescription());
		
		System.out.println("********************");
		
		System.out.println("Broj elemenata u paketu : " + packageUnit.getPackageElements().size()); 
		
		System.out.println("Ime Paketa : " + packageUnit.getNameOfPackage());
		
		packageService.save(packageUnit);

		/*Long newRoundId = addExerciseInRound(exerciseInRoundDTO, "add");
		trainingId = roundService.findOne(exerciseInRoundDTO.getRoundId()).getTraining().getId();
		obj.put("roundId", newRoundId);
		obj.put("exerciseExecId", newExerciseInRoundExecId);
*/


	return ResponseEntity.ok(obj.toString());
}
/*
private Long addExerciseInRound(ExerciseInRoundDTO exerciseInRoundDTO, String mode) {
	ExerciseInRound exerciseInRound;
	exerciseInRound = exerciseInRoundService.save(exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO));
	newExerciseInRoundExecId = exerciseInRound.getExecInRound_Id();
	return exerciseInRound.getRound().getId();
}*/
}
