package training.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import training.dto.TrainerTrainingReportDTO;
import training.emailService.MailService;
import training.model.Client;
import training.model.Operator;
import training.model.Training;
import training.repository.OperatorRepository;
import training.repository.TrainingRepository;
import training.service.OperatorService;
import training.util.PasswordGenUtil;
import training.util.PdfGenaratorUtil;

@RestController
public class RestOperatorController {
	
	@Autowired
	private OperatorService operatorService;
	
	@Autowired
	private OperatorRepository operatorRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TrainingRepository trainingRepository;
	
	@Autowired
	private PdfGenaratorUtil pdfGenaratorUtil;
	
	@PostMapping(value = { "/sendPasswordToEmail" })
	public ResponseEntity<?> sendPasswordToEmail(@Valid @RequestBody OperatorDTO operatorDTO) {
		Operator operator = operatorService.findOneByEmail(operatorDTO.getEmail());
		
		String newPassword = PasswordGenUtil.alphaNumericString(10);
		operator.setPassword(passwordEncoder.encode(newPassword));
		operatorService.save(operator);
		mailService.sendEmail(operator, newPassword);
		
		JSONObject obj = new JSONObject();
		return ResponseEntity.ok(obj.toString());
	}
	
	@PostMapping(value = { "/trainerTrainingsReport" })
	public ResponseEntity<?> trainerTrainingsReport(@Valid @RequestBody TrainerTrainingReportDTO trainerTrainingReportDTO) {
	
		int isThereError = -1;
		if(trainerTrainingReportDTO.getStartDate() != null && trainerTrainingReportDTO.getEndDate() != null && trainerTrainingReportDTO.getHighlightedTrainingId() != null) {
		String startDateString = trainerTrainingReportDTO.getStartDate().toString();
		String endDateString = trainerTrainingReportDTO.getEndDate().toString();		
		String[] startDateStringArray = startDateString.split(" ");
		String[] endtDateStringArray = endDateString.split(" ");
		
		String startDateStringRework = startDateStringArray[2] + " " + startDateStringArray[1] + " " + startDateStringArray[5];
		String endDateStringRework = endtDateStringArray[2] + " " + endtDateStringArray[1] + " " + endtDateStringArray[5];
		
		List<Training> listTrainings = trainingRepository.getForTrainerInInterval(trainerTrainingReportDTO.getHighlightedTrainingId(), trainerTrainingReportDTO.getStartDate(), trainerTrainingReportDTO.getEndDate());
		Map<String, Object> data = new HashMap<String, Object>();
		Operator operatorInQuestion = operatorRepository.findOne(trainerTrainingReportDTO.getHighlightedTrainingId());

		if(operatorInQuestion.getPersonalName() != null && operatorInQuestion.getFamilyName() != null) {
			data.put("name", operatorInQuestion.getPersonalName() + " " + operatorInQuestion.getFamilyName());
		} else {
			data.put("name", operatorInQuestion.getUserName());
		}
		data.put("startDate", startDateStringRework);
		data.put("endDate", endDateStringRework);
		
		List<String> dateList = new ArrayList<String>();
		for(Training training : listTrainings) {
			System.out.println(training.getDate());
			String[] iteratedTrainingDate = training.getDate().toString().split(" ");
			String dateWithoutTime = iteratedTrainingDate[0];
			String[] dayMontheYear = dateWithoutTime.split("-");
			dateList.add(dayMontheYear[2] + "." + dayMontheYear[1] + "." + dayMontheYear[0] + " - " + training.getClient().getName() + " " + training.getClient().getFamilyName());
		}
		data.put("listOfTrainings", dateList);
		data.put("numberOfTrainings", listTrainings.size());		 		 
		try {
			isThereError = pdfGenaratorUtil.clientReportPdf("PDFTemplateTrainerTrainings",data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		JSONObject obj = new JSONObject();
		return ResponseEntity.ok(obj.toString());
	}
}
