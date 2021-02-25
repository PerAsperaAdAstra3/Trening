package training.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import training.converter.OperatorDTOtoOperator;
import training.converter.OperatorToOperatorDTO;
import training.dto.OperatorDTO;
import training.dto.PasswordChangeDTO;
import training.emailService.MailService;
import training.enumerations.Roles;
import training.model.Operator;
import training.service.OperatorService;
import training.util.LoggingUtil;
import training.util.PasswordGenUtil;

@Controller
public class OperatorController {

	@Autowired
	private OperatorToOperatorDTO operatorToOperatorDTO;

	@Autowired
	private OperatorService operatorService;

	@Autowired
	private OperatorDTOtoOperator operatorDTOtoOperator;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailService mailService;
	
	@Value("${logging.path}")
	private String loggingPath;
	
	@Value("${logging.file}")
	private String loggingFile;
	
	@Value("${pdf.folder}")
	private String pdfFolder;
	
	@Value("${log.file}")
	private String logFile;
	
	private boolean nameTaken = false;
	private boolean emailTaken = false;
	private boolean emailFormatBad = false;
	
	Logger logger = LoggerFactory.getLogger(OperatorController.class);
	
	@RequestMapping(value = { "/operatorList" }, method = RequestMethod.GET)
	public String getClients(Model model) {
		
		List<Operator> operators = operatorService.findAll();
		
		for(Operator operator : operators) {
			if(operator.getAuthorities().equals(Roles.SUPERUSER.getNameText())) {
				
				operators.remove(operator);
			}
		}

		model.addAttribute("operatorDTOSearch", new OperatorDTO());
		model.addAttribute("operatorDTO", new OperatorDTO());
		model.addAttribute("operators", operatorToOperatorDTO.convert(operators));
		model.addAttribute("authorities", authorities());
		model.addAttribute("emailFormatBad", emailFormatBad);
		model.addAttribute("usernameTaken", nameTaken);
		model.addAttribute("emailTaken", emailTaken);
		model.addAttribute("pageTitle", "Korisnici u sistemu");
		return "operator";
	}
	
	private List<String> authorities(){
		List<String> authorities = new ArrayList<String>();
		authorities.add(Roles.FRONTDESK.getNameText());
		authorities.add(Roles.ADMIN.getNameText());
		authorities.add(Roles.TRAINER.getNameText());
		return authorities;
	}
	
	@RequestMapping(value = {"/addOperator"}, method = RequestMethod.POST)
	public String addOperator(Model model, @ModelAttribute("operatorDTO") OperatorDTO operatorDTO, @RequestParam String mode) {
		nameTaken = false;
		emailTaken = false;
		emailFormatBad = false;

		try {
		String ss = PasswordGenUtil.alphaNumericString(10);
		mailService.sendEmail(operatorDTO, ss);
		operatorDTO.setPassword(passwordEncoder.encode(ss));

		List<Operator> operatorsByUsername = operatorService.findByUsername(operatorDTO.getUserName());
		List<Operator> operatorsByEmail = operatorService.findByEmail(operatorDTO.getEmail());
		
		boolean itCanBeAdded = true;
		if("add".equals(mode)) {
			operatorDTO.setId(null);
			if(operatorsByUsername.size() > 0) {
				nameTaken = true;
				itCanBeAdded = false;
			}
			if(operatorsByEmail.size() > 0) {
				emailTaken = true;
				itCanBeAdded = false;
			}
			if(itCanBeAdded) {
				nameTaken = false;
				emailTaken = false;
				operatorService.save(operatorDTOtoOperator.convert(operatorDTO));
			}
		} else {
			operatorService.edit(operatorDTO.getId(), operatorDTOtoOperator.convert(operatorDTO));
		}
		
		} catch (Exception e) {
			emailFormatBad = true;
			e.printStackTrace();
		}
		
		return "redirect:/operatorList";
	}
	
	@RequestMapping(value = {"/deleteOperator/{id}"}, method = RequestMethod.GET)
	public String deleteOperator(@PathVariable String id ) {
		nameTaken = false;
		emailTaken = false;
		emailFormatBad = false;
		try {
			operatorService.delete(Long.parseLong(id));
		} catch (NumberFormatException numberFormatException) {
			LoggingUtil.LoggingMethod(logger, numberFormatException);
		} catch (IllegalArgumentException illegalArgumentException) {
			LoggingUtil.LoggingMethod(logger, illegalArgumentException);
		} catch (Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
		}
		return "redirect:/operatorList";
	}
	
	@RequestMapping(value = {"/personalInfoManagementCtrl"}, method = RequestMethod.GET)
	public String personalInfoManagement(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		
		model.addAttribute("operatorDTO", operatorToOperatorDTO.convert(operatorService.findOneByUserName(username)));
		model.addAttribute("authorities", authorities());
		model.addAttribute("pageTitle", "Lična podešavanja");
		return "personalInfoManagement";
	}
	
/*	@RequestMapping(value = {"/openLogFile"}, method = RequestMethod.GET)
	public String openLogFile(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		
		System.out.println(loggingPath);
		System.out.println(loggingFile);
		System.out.println(pdfFolder);		
		System.out.println(logFile);

		try {
			Runtime.getRuntime().exec("explorer.exe /open," + loggingPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = {"/openPDFFolder"}, method = RequestMethod.GET)
	public String openPDFFolder(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		
		System.out.println(loggingPath);
		System.out.println(loggingFile);
		System.out.println(pdfFolder);		
		System.out.println(logFile);

		try {
			Runtime.getRuntime().exec("explorer.exe /open," + pdfFolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/";
	}*/
	
	@RequestMapping(value = {"/editSelf"}, method = RequestMethod.POST)
	public String editSelf(Model model, @ModelAttribute("operatorDTO") OperatorDTO operatorDTO, @RequestParam String mode){
		
			Operator operatorVar = operatorService.findOne(operatorDTO.getId());
			operatorVar.setPersonalName(operatorDTO.getPersonalName());
			operatorVar.setFamilyName(operatorDTO.getFamilyName());
			operatorVar.setEmail(operatorDTO.getEmail());
			
			operatorService.save(operatorVar);

		return "redirect:/";
	}
	
	@RequestMapping(value = {"/changePasswordCtrl"}, method = RequestMethod.GET)
	public String changePassword(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		
		PasswordChangeDTO passwordChangeDTO = new PasswordChangeDTO();
		
		model.addAttribute("passwordChangeDTO", passwordChangeDTO);
		model.addAttribute("outcomeMessage", "start");
		
		return "changePassword";
	}
	
	@RequestMapping(value = {"/changePasswordActual"}, method = RequestMethod.POST)
	public String changePasswordActual(Model model, @ModelAttribute("operatorDTO") PasswordChangeDTO passwordChangeDTO) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		
		Operator currentOperator = operatorService.findOneByUserName(username);
		
		if(passwordEncoder.matches(passwordChangeDTO.getOldPassword(), currentOperator.getPassword())) {
			if(passwordChangeDTO.getNewPassword1().equals(passwordChangeDTO.getNewPassword2())) {
				currentOperator.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword1()));
				operatorService.save(currentOperator);
				model.addAttribute("outcomeMessage", "allok");
			} else {
				model.addAttribute("outcomeMessage", "passwordsDontMach");
			}
		} else {
			model.addAttribute("outcomeMessage", "wrongOldPassword");
		}
		PasswordChangeDTO passwordChangeDTO1 = new PasswordChangeDTO();
		model.addAttribute("passwordChangeDTO", passwordChangeDTO1);
		model.addAttribute("pageTitle", "Promena lozinke");
		return "changePassword";
	}
	
	@RequestMapping(value = {"/sendEmail"}, method = RequestMethod.POST)
	public String sendEmail(Model model, @RequestParam String emailAddress) {
		Operator operator = operatorService.findOneByEmail(emailAddress);
		
		if(operator != null) {
			String newPassword = PasswordGenUtil.alphaNumericString(10);
			operator.setPassword(passwordEncoder.encode(newPassword));
			operatorService.save(operator);
			mailService.sendEmail(operator, newPassword);
		} else {
			model.addAttribute("emailNotInDB", true);
		}
			
		return "login";
	}
	
}
