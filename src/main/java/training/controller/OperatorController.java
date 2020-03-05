package training.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
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
import training.model.Operator;
import training.service.OperatorService;
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
    private JavaMailSender javaMailSender;

	@Autowired
	private MailService mailService;
	
	private String nameTaken = "notTaken";
	private String emailTaken = "notTaken";
	private String emailFormatBad = "formatok";
	
	@RequestMapping(value = { "/operatorList" }, method = RequestMethod.GET)
	public String getClients(Model model) {
		
		model.addAttribute("operatorDTOSearch", new OperatorDTO());
		model.addAttribute("operatorDTO", new OperatorDTO());
		model.addAttribute("operators", operatorToOperatorDTO.convert(operatorService.findAll()));
		model.addAttribute("authorities", authorities());
		model.addAttribute("emailFormatBad", emailFormatBad);
		model.addAttribute("usernameTaken", nameTaken);
		model.addAttribute("emailTaken", emailTaken);
		return "operator";
	}
	
	private List<String> authorities(){
		List<String> authorities = new ArrayList<String>();
		authorities.add("RECEPCIJA");
		authorities.add("ADMIN");
		authorities.add("TRENER");
		return authorities;
	}
	
	@RequestMapping(value = {"/addOperator"}, method = RequestMethod.POST)
	public String addOperator(Model model, @ModelAttribute("operatorDTO") OperatorDTO operatorDTO, @RequestParam String mode) {
		nameTaken = "nottaken";
		emailTaken = "nottaken";
		emailFormatBad = "formatok";

		try {
		String ss = PasswordGenUtil.alphaNumericString(10);
		mailService.sendEmail(operatorDTO, ss);
		operatorDTO.setPassword(passwordEncoder.encode(ss)); //operatorDTO.getPassword()));
		List<Operator> operators = operatorService.findAll();
		boolean itCanBeAdded = true;
		if("add".equals(mode)) {
			operatorDTO.setId(null);
			for(Operator operator : operators) {
				if(operator.getUserName().equals(operatorDTO.getUserName())) {
					nameTaken = "taken";
					itCanBeAdded = false;
					break;
				}
				if(operator.getEmail().equals(operatorDTO.getEmail())) {
					emailTaken = "taken";
					itCanBeAdded = false;
					break;
				}
			}
			if(itCanBeAdded) {
				nameTaken = "notTaken";
				emailTaken = "notTaken";
				operatorService.save(operatorDTOtoOperator.convert(operatorDTO));
			}
		} else {
			operatorService.edit(operatorDTO.getId(), operatorDTOtoOperator.convert(operatorDTO));
		}
		
		} catch (Exception e) {
			emailFormatBad = "formatbad";
			e.printStackTrace();
		}
		

		model.addAttribute("emailFormatBad", emailFormatBad);
		model.addAttribute("usernameTaken", nameTaken);
		model.addAttribute("emailTaken", emailTaken);
		
		return "redirect:/operatorList";
	}
	
	@RequestMapping(value = {"/deleteOperator/{id}"}, method = RequestMethod.GET)
	public String deleteOperator(@PathVariable String id ) {
		operatorService.delete(Long.parseLong(id));
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
		
		model.addAttribute("operatorDTO", operatorToOperatorDTO.convert(operatorService.findByUsername(username).get(0)));
		model.addAttribute("authorities", authorities());
		return "personalInfoManagement";
	}
	
	@RequestMapping(value = {"/editSelf"}, method = RequestMethod.POST)
	public String editSelf(Model model, @ModelAttribute("operatorDTO") OperatorDTO operatorDTO, @RequestParam String mode){
		//operatorDTO.setPassword(passwordEncoder.encode(operatorDTO.getPassword()));
	//	if("add".equals(mode)) {
			Operator operatorVar = operatorService.findOne(operatorDTO.getId());
			operatorVar.setPersonalName(operatorDTO.getPersonalName());
			operatorVar.setFamilyName(operatorDTO.getFamilyName());
			operatorVar.setEmail(operatorDTO.getEmail());
			
			
			//operatorDTO.setId(null);
			operatorService.save(operatorVar); //operatorDTOtoOperator.convert(operatorDTO));
	//	} else {
	//		operatorService.edit(operatorDTO.getId(), operatorDTOtoOperator.convert(operatorDTO));
	//	}
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
		Operator currentOperator = operatorService.findByUsername(username).get(0);
		
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
		
		Operator currentOperator = operatorService.findByUsername(username).get(0);
		
		String oldPassword = passwordEncoder.encode(passwordChangeDTO.getOldPassword());
		
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
		
		return "changePassword";
	}
	
	@RequestMapping(value = {"/sendEmail"}, method = RequestMethod.POST)
	public String sendEmail(Model model, @RequestParam String emailAddress) {

		List<Operator> operator = operatorService.findByEmail(emailAddress);
		
		if(operator.size() > 0 ) {
			String newPassword = PasswordGenUtil.alphaNumericString(10);
			operator.get(0).setPassword(passwordEncoder.encode(newPassword));
			operatorService.save(operator.get(0));
			mailService.sendEmail(operator.get(0), newPassword);
		} else {
			model.addAttribute("emailNotInDB", true);
		}
			
		return "login";
	}
	
}
