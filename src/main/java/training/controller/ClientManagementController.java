package training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import training.converter.ClientDTOtoClient;
import training.converter.ClientPackageDTOtoClientPackage;
import training.converter.ClientPackageToClientPackageDTO;
import training.converter.ClientToClientDTO;
import training.converter.PackageDTOtoPackage;
import training.converter.PackageToPackageDTO;
import training.dto.ClientDTO;
import training.model.Client;
import training.model.ClientPackage;
import training.model.ClientPackageElement;
import training.model.Package;
import training.service.ClientPackageElementService;
import training.service.ClientPackageService;
import training.service.ClientService;
import training.service.PackageService;

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

//@Autowired
//ClientPackageElementToClientPackageElementDTO clientPackageElementToClientPackageElementDTO;

	@RequestMapping(value = { "/clientManagement/{id}" }, method = RequestMethod.GET)
	public String clientManagement(Model model, @PathVariable String id) {

		System.out.println("CLient id : "+id);
		List<Package> packageList = packageService.findAll();
		List<Client> client = clientService.findAll();
		List<ClientPackage> clientPackage = clientPackageService.findAll();
		List<ClientPackageElement> clientPackageElement = clientPackageElementService.findAll();
		
		List<ClientPackage> clientPackageForClient = clientPackageService.filter(clientService.findOne(Long.parseLong(id)));
		
		model.addAttribute("clientId", id);
		model.addAttribute("client", clientToClientDTO.convert(clientService.findOne(Long.parseLong(id))));
		model.addAttribute("clientDTOSearch", new ClientDTO());
		model.addAttribute("clientDTO", new ClientDTO());
		model.addAttribute("clients", clientToClientDTO.convert(clientService.findAll()));
		model.addAttribute("allPackages", packageToPackageDTO.convert(packageList));
		
		model.addAttribute("clientPackages", clientPackageToClientPackageDTO.convert(clientPackageService.filter(clientService.findOne(Long.parseLong(id)))));
		
		model.addAttribute("clientPackagesForClient", clientPackageForClient);
		
		return "clientManagement";
	}

}
