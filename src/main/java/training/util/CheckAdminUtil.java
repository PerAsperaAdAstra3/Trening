package training.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import training.model.Operator;
import training.service.OperatorService;

@Component
public class CheckAdminUtil {
	@Autowired
	OperatorService operatorService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostConstruct
	public void checkAdming() {
		List<Operator> operatorList = operatorService.findAll();
		boolean isThereAdmin = false;
		for(Operator operator : operatorList) {
			if(operator.getAuthorities().equals("ADMIN")){
				isThereAdmin = true;
			}
		}
		if(!isThereAdmin) {
			Operator operatorx = new Operator();
			operatorx.setUserName("ADMIN");
			operatorx.setPassword(passwordEncoder.encode("ADMIN"));
			operatorx.setAuthorities("ADMIN");
			operatorService.save(operatorx);
		}
	}
}
