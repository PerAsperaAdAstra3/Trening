package training.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import training.emailService.MailService;
import training.model.Operator;
import training.service.OperatorService;

@Component
public class CheckAdminUtil {
	@Autowired
	OperatorService operatorService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired 
	Environment environment;
	
	@Autowired
	private MailService mailService;
	
	@PostConstruct
	public void checkAdming() throws Exception {
		List<Operator> operatorList = operatorService.findAll();
		boolean isThereAdmin = false;
		for(Operator operator : operatorList) {
			if(operator != null) {
				if(operator.getAuthorities() != null && operator.getAuthorities().equals("ADMIN")){
					isThereAdmin = true;
				}
			}
		}
		if(!isThereAdmin) {
			Operator operatorx = new Operator();
			String newPassword = PasswordGenUtil.alphaNumericString(10);
			operatorx.setUserName(PasswordGenUtil.alphaNumericString(10)); //TODO Should this be random?
			operatorx.setPassword(passwordEncoder.encode(newPassword));
			operatorx.setAuthorities("ADMIN");
			operatorx.setEmail(this.environment.getProperty("business.mail"));
			System.out.println(operatorx.getUserName());
			System.out.println(operatorx.getPassword());
			System.out.println(operatorx.getAuthorities());
			System.out.println(operatorx.getEmail());
			operatorService.save(operatorx);
			
			mailService.sendEmail(operatorx, newPassword);
		}
	}
}
