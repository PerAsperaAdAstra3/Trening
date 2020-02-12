package training.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import training.model.ElementsInPackages;
import training.converter.ElementsInPackagesToElementsInPackagesDTO;
import training.converter.PackageDTOtoPackage;
import training.converter.PackageElementDTOtoPackageElement;
import training.converter.PackageElementToPackageElementDTO;
import training.converter.PackageToPackageDTO;
import training.dto.PackageDTO;
import training.dto.PackageElementDTO;
import training.service.ElementsInPackagesService;
import training.service.PackageElementService;
import training.service.PackageService;

@Controller
public class PackageController {

	@Autowired
	PackageToPackageDTO packageToPackageDTO;
	
	@Autowired
	PackageService packageService;
	
	@Autowired
	PackageElementService packageElementService;
	
	@Autowired
	PackageElementDTOtoPackageElement packageElementDTOtoPackageElement;
	
	@Autowired
	PackageElementToPackageElementDTO packageElementToPackageElementDTO;
	
	@Autowired
	PackageDTOtoPackage packageDTOtoPackage;
	
	@Autowired
	ElementsInPackagesService elementsInPackagesService;
	
	@Autowired
	ElementsInPackagesToElementsInPackagesDTO elementsInPackagesToElementsInPackagesDTO;
	
	@RequestMapping(value = { "/packageList" }, method = RequestMethod.GET)
	public String getClients(Model model) {
		List<PackageDTO> packageList = packageToPackageDTO.convert(packageService.findAll());
		
		List<ElementsInPackages> elementsInPackages = new ArrayList<ElementsInPackages>();
		
	/*	for(Package packageIter : packageService.findAll()) {
			for(PackageElementDTO packageElementDTO : packageElementToPackageElementDTO.convert(packageIter.getElementsInPackages())) {
				listOfAllpackageElements.add(packageElementDTO);
			}
		}*/
//		List<ArrayList<PackageElementDTO>> listOfpackageElementLists = new ArrayList<ArrayList<PackageElementDTO>>();
		
		model.addAttribute("packageDTOSearch", new PackageDTO());
		model.addAttribute("packageDTO", new PackageDTO());
		model.addAttribute("packageElementDTOSearch", new PackageElementDTO());
		model.addAttribute("packageElementDTO", new PackageElementDTO());
		model.addAttribute("packages", packageToPackageDTO.convert(packageService.findAll()));
		model.addAttribute("packageElements", packageElementToPackageElementDTO.convert(packageElementService.findAll()));
		model.addAttribute("elementsInPackages", elementsInPackagesToElementsInPackagesDTO.convert(elementsInPackagesService.findAll()));
		return "packagePage";
	}
	
	@RequestMapping(value = {"/addPackageElement"} , method = RequestMethod.POST)
	public String addPackageElement(Model model, @ModelAttribute("packageElementDTO") PackageElementDTO packageElementDTO, @RequestParam String mode){

		if("add".equals(mode)) {
			packageElementDTO.setPackageElementID(null);
			packageElementService.save(packageElementDTOtoPackageElement.convert(packageElementDTO));
		} else {
			packageElementService.edit(packageElementDTO.getPackageElementID(), packageElementDTOtoPackageElement.convert(packageElementDTO));
		}
		return "redirect:/packageList";
	}
	
	@RequestMapping(value = {"/addPackage"} , method = RequestMethod.POST)
	public String addPackage(Model model, @ModelAttribute("packageDTO") PackageDTO packageDTO, @RequestParam String mode){

		System.out.println(packageDTO.getNameOfPackage());
		System.out.println(packageDTO.getPrice());
		
		if("add".equals(mode)) {
			packageDTO.setId(null);
			packageService.save(packageDTOtoPackage.convert(packageDTO));
		} else {
			packageService.edit(packageDTO.getId(), packageDTOtoPackage.convert(packageDTO));
		}
		return "redirect:/packageList";
	}
	
	@RequestMapping(value = {"/deletePackageElement/{id}"}, method = RequestMethod.GET)
	public String deletePackageElement(@PathVariable String id) {
		packageElementService.delete(Long.parseLong(id));
		return "redirect:/packageList";
	}
	
	@RequestMapping(value = {"/deletePackage/{id}"}, method = RequestMethod.GET)
	public String deletePackage(@PathVariable String id) {
		packageService.delete(Long.parseLong(id));
		return "redirect:/packageList";
	}
}