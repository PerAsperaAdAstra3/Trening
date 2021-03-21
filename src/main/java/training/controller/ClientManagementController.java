package training.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import training.converter.ClientDTOtoClient;
import training.converter.ClientPackageDTOtoClientPackage;
import training.converter.ClientPackageElementToClientPackageElementDTO;
import training.converter.ClientPackageToClientPackageDTO;
import training.converter.ClientToClientDTO;
import training.converter.ElementsInPackagesToElementsInPackagesDTO;
import training.converter.PackageDTOtoPackage;
import training.converter.PackageToPackageDTO;
import training.dto.ClientPackageDTO;
import training.model.Client;
import training.model.ClientPackage;
import training.model.ClientPackageElement;
import training.model.Package;
import training.service.ClientPackageElementService;
import training.service.ClientPackageService;
import training.service.ClientService;
import training.service.ElementsInPackagesService;
import training.service.PackageService;
import training.util.LoggingUtil;

@Controller
public class ClientManagementController {
	
@Autowired
PackageService packageService;

@Autowired
ClientService clientService;

@Autowired
ClientPackageService clientPackageService;

@Autowired
ClientPackageElementService clientPackageElementService;

@Autowired
PackageDTOtoPackage packageDTOtoPackage;

@Autowired
PackageToPackageDTO packageToPackageDTO;

@Autowired
ClientToClientDTO clientToClientDTO;

@Autowired
ClientDTOtoClient clientDTOtoClient;

@Autowired
ClientPackageDTOtoClientPackage clientPackageDTOtoClientPackage;

@Autowired
ClientPackageToClientPackageDTO clientPackageToClientPackageDTO;

@Autowired
ClientPackageElementToClientPackageElementDTO clientPackageElementToClientPackageElementDTO;

@Autowired
ElementsInPackagesToElementsInPackagesDTO elementsInPackagesToElementsInPackagesDTO;

@Autowired
ElementsInPackagesService elementsInPackagesService;

Logger logger = LoggerFactory.getLogger(ClientManagementController.class);

	@RequestMapping(value = { "/clientManagement/{id}" }, method = RequestMethod.GET)
	public String clientManagement(Model model, @PathVariable String id) {

		List<Package> packageList = packageService.findAll();
		List<ClientPackage> clientPackageForClient = clientPackageService.filter(Long.parseLong(id)); 
		List<ClientPackageDTO> clientPackageDTOList = clientPackageToClientPackageDTO.convert(clientPackageForClient);
		Collections.reverse(clientPackageDTOList);
		 
		for(ClientPackageDTO var1 : clientPackageDTOList) {
			if(var1.getPriceOfClientPackage() == null) {
				var1.setPriceOfClientPackage(0l);
			}
		}
		Client client = new Client();
		model.addAttribute("clientId", id);
		try {
			client = clientService.findOne(Long.parseLong(id));
			model.addAttribute("client", clientToClientDTO.convert(client));//Information of client in question.
		} catch (NumberFormatException numberFormatException) {
			LoggingUtil.LoggingMethod(logger, numberFormatException);
		} catch (IllegalArgumentException illegalArgumentException) {
			LoggingUtil.LoggingMethod(logger, illegalArgumentException);
		} catch (Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
		}
		model.addAttribute("allPackages", packageToPackageDTO.convert(packageList));	//List of all packages that exist in the system. These can be added to client.
		model.addAttribute("clientPackages", clientPackageDTOList);	// List of client packages.
		model.addAttribute("pageTitle", client.getName() + " " + client.getFamilyName() + " - Paketi"); 
		List<ClientPackageElement> clientPackageElementList = new ArrayList<ClientPackageElement>();
		

		clientPackageElementList.addAll(clientPackageElementService.filter(clientPackageForClient));
				
		model.addAttribute("clientPackageElements", clientPackageElementToClientPackageElementDTO.convert(clientPackageElementList));
		model.addAttribute("elementsInPackages", elementsInPackagesToElementsInPackagesDTO.convert(elementsInPackagesService.findAll()));
		
		return "clientManagement";
	}

}
