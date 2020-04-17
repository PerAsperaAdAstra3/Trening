package training.emailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import training.dto.OperatorDTO;
import training.model.Operator;

@Service
public class MailService {
	
@Autowired
private JavaMailSender javaMailSender;

@Autowired Environment environment;
	
public void sendEmail(Operator operator, String newPassword) throws MailException{
	SimpleMailMessage msg = new SimpleMailMessage();
	msg.setTo(operator.getEmail());
	msg.setFrom(this.environment.getProperty("business.mail"));
	msg.setSubject("Korisničko ime i lozinka");
	msg.setText("Vaše korisničko ime je : " + operator.getUserName() + "\nVaša lozinka je : "
				+ newPassword + "\n\nIz bezbednosnih razloga promenite lozinku nakon što se ulogujete.");
	javaMailSender.send(msg);
}
	
public void sendEmail(OperatorDTO operatorDTO, String message) throws MailException{
	SimpleMailMessage msg = new SimpleMailMessage();
	msg.setTo(operatorDTO.getEmail());
	msg.setFrom(this.environment.getProperty("business.mail"));
	msg.setSubject("Lozinka");
	msg.setText("Vaše korisničko ime je : " + operatorDTO.getUserName() + "\nVaša lozinka je : " + message);
	javaMailSender.send(msg);
}
}
