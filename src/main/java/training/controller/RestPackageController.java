package training.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import training.dto.ElementsInPackagesDTO;
import training.dto.ElementsInPackagesDTOAjax;
import training.dto.PackageDTOAjax;
import training.model.ElementsInPackages;
import training.model.Package;
import training.model.PackageElement;
import training.service.ElementsInPackagesService;
import training.service.PackageElementService;
import training.service.PackageService;
import training.util.LoggingUtil;

@RestController
public class RestPackageController {

	@Autowired
	PackageService packageService;

	@Autowired
	PackageElementService packageElementService;

	@Autowired
	ElementsInPackagesService elementsInPackagesService;

	Logger logger = LoggerFactory.getLogger(RestTrainingController.class);
	
	@PostMapping(value = { "/addPackagePackageElement"})
	public ResponseEntity<?> addPackagePackageElement(@Valid @RequestBody PackageDTOAjax packageDTOAjax) {
		JSONObject obj = new JSONObject();
		Package packageUnit = packageService.findOne(packageDTOAjax.getId());
		Long trainingId = -1l;

		List<ElementsInPackagesDTO> elementsInPackagesDTOList = new ArrayList<ElementsInPackagesDTO>();
		
		PackageElement packageElement = packageElementService.findOne(packageDTOAjax.getPackageElementId() );
		ElementsInPackages elementsInPackages = new ElementsInPackages();
		elementsInPackages.setPackage(packageUnit);
		elementsInPackages.setPackageElementEIP(packageElementService.findOne(packageDTOAjax.getPackageElementId()));
		elementsInPackages.setNumber(packageDTOAjax.getNumnerOfElements());
		
		ElementsInPackages filteredNewElementsInPackage = elementsInPackagesService.filter(packageService.findOne(packageDTOAjax.getId()) , packageElementService.findOne(packageDTOAjax.getPackageElementId()));
		
		ElementsInPackages elementsInPackagesNew = new ElementsInPackages();
		Long elementsInPackageID ;

		if(null != filteredNewElementsInPackage) {
			obj.put("modOfOperation", "edit");
			elementsInPackages.setNumber(packageDTOAjax.getNumnerOfElements() + filteredNewElementsInPackage.getNumber());
			elementsInPackageID = filteredNewElementsInPackage.getElemInPackagesId();
			elementsInPackagesService.edit(filteredNewElementsInPackage.getElemInPackagesId(), elementsInPackages);
		} else {
			obj.put("modOfOperation", "add");
			elementsInPackages = elementsInPackagesService.save(elementsInPackages);
			elementsInPackageID = elementsInPackages.getElemInPackagesId();
			packageUnit.addElementsInPackages(elementsInPackages);
			packageService.save(packageUnit);
		}
		
		obj.put("packElName", packageElement.getName());
		obj.put("packElDescription", packageElement.getDescription());
		obj.put("packElId", elementsInPackages.getPackageElementEIP().getPackageElementID());
		obj.put("elementsInPackagesNumber", elementsInPackages.getNumber());
		obj.put("packElpackageId", elementsInPackages.getPackage().getId());
		obj.put("elemInPackagesId", elementsInPackageID);

		return ResponseEntity.ok(obj.toString());
	}

	@PostMapping(value = { "/deleteElementsInPackages"})
	public ResponseEntity<?> addDeleteElementsInPackages(@Valid @RequestBody ElementsInPackagesDTOAjax elementsInPackagesDTOAjax) {
		JSONObject obj = new JSONObject();

		if(null != elementsInPackagesDTOAjax.getNumber() && elementsInPackagesDTOAjax.getNumber() > 0) {
		ElementsInPackages elementsInPackages = elementsInPackagesService.findOne(Long.parseLong(elementsInPackagesDTOAjax.getId()));
		elementsInPackages.setNumber(elementsInPackagesDTOAjax.getNumber());
		elementsInPackagesService.edit(Long.parseLong(elementsInPackagesDTOAjax.getId()), elementsInPackages);
		} else {
			try {
				ElementsInPackages elementsInPackages = elementsInPackagesService.delete(Long.parseLong(elementsInPackagesDTOAjax.getId()));
				obj.put("zeroLeft", "yes");
			} catch(Exception e) {
				LoggingUtil.LoggingMethod(logger, e);
			}
		}
		
		return ResponseEntity.ok(obj.toString());
	}
}
