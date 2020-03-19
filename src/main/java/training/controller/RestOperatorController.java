package training.controller;

import java.util.List;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import training.converter.OperatorDTOtoOperator;
import training.dto.OperatorDTO;
import training.emailService.MailService;
import training.model.Operator;
import training.service.OperatorService;
import training.util.PasswordGenUtil;

@RestController
public class RestOperatorController {
	
	@Autowired
	private OperatorDTOtoOperator operatorDTOtoOperator;
	
	@Autowired
	private OperatorService operatorService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping(value = { "/sendPasswordToEmail" })
	public ResponseEntity<?> sendPasswordToEmail(@Valid @RequestBody OperatorDTO operatorDTO) {
		List<Operator> operator = operatorService.findByEmail(operatorDTO.getEmail());
		
		String newPassword = PasswordGenUtil.alphaNumericString(10);
		operator.get(0).setPassword(passwordEncoder.encode(newPassword));
		operatorService.save(operator.get(0));
		mailService.sendEmail(operator.get(0), newPassword);
		
		JSONObject obj = new JSONObject();
		return ResponseEntity.ok(obj.toString());
	}
}
