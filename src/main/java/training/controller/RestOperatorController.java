package training.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import training.converter.OperatorDTOtoOperator;
import training.dto.OperatorDTO;
import training.dto.TrainerTrainingReportDTO;
import training.dto.TrainerTrainingReportDataDTO;
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
	
	@Value("${logging.path}")
	private String loggingPath;
	
	@Value("${logging.file}")
	private String loggingFile;
	
	@Value("${pdf.folder}")
	private String pdfFolder;
	
	@Value("${log.file}")
	private String logFile;
	
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
		JSONObject obj = new JSONObject();
		int isThereError = -1;
		
		if(trainerTrainingReportDTO.getEndDate() == null) {
			trainerTrainingReportDTO.setEndDate(new Date());
		}
		
		if(trainerTrainingReportDTO.getStartDate() == null) {
			trainerTrainingReportDTO.setStartDate(trainerTrainingReportDTO.getEndDate());
		}
		
		if(trainerTrainingReportDTO.getStartDate() != null && trainerTrainingReportDTO.getEndDate() != null && trainerTrainingReportDTO.getHighlightedTrainingId() != null) {
		String startDateString = trainerTrainingReportDTO.getStartDate().toString();
		String endDateString = trainerTrainingReportDTO.getEndDate().toString();		
		String[] startDateStringArray = startDateString.split(" ");
		String[] endtDateStringArray = endDateString.split(" ");
		
		String startDateStringRework = startDateStringArray[2] + " " + startDateStringArray[1] + " " + startDateStringArray[5];
		String endDateStringRework = endtDateStringArray[2] + " " + endtDateStringArray[1] + " " + endtDateStringArray[5];
		
		List<Training> listTrainings = trainingRepository.getForTrainerInInterval(trainerTrainingReportDTO.getHighlightedTrainingId(), trainerTrainingReportDTO.getStartDate(), trainerTrainingReportDTO.getEndDate());
		Map<String, Object> data = new HashMap<String, Object>();
		Operator operatorInQuestion = operatorRepository.findById(trainerTrainingReportDTO.getHighlightedTrainingId()).get();

		if(operatorInQuestion.getPersonalName() != null && operatorInQuestion.getFamilyName() != null) {
			data.put("name", operatorInQuestion.getPersonalName() + " " + operatorInQuestion.getFamilyName());
			obj.put("name", operatorInQuestion.getPersonalName() + " " + operatorInQuestion.getFamilyName());
		} else {
			data.put("name", operatorInQuestion.getUserName());
			obj.put("name", operatorInQuestion.getUserName());
		}
		data.put("startDate", startDateStringRework);
		data.put("endDate", endDateStringRework);
		
		List<String> dateList = new ArrayList<String>();
		for(Training training : listTrainings) {
			String[] iteratedTrainingDate = training.getDate().toString().split(" ");
			String dateWithoutTime = iteratedTrainingDate[0];
			String[] dayMontheYear = dateWithoutTime.split("-");
			dateList.add(dayMontheYear[2] + "." + dayMontheYear[1] + "." + dayMontheYear[0] + " - " + training.getClient().getName() + " " + training.getClient().getFamilyName());
		}
		data.put("listOfTrainings", dateList);
		data.put("numberOfTrainings", listTrainings.size());		
		if(listTrainings.size() == 0) {
	    	obj.put("successMessage", "Trener još nema urađenih treninga!");
			return ResponseEntity.ok(obj.toString());
		}
		
//		obj.put("name", clientInQuestion.getName() + " "  + clientInQuestion.getFamilyName());
		obj.put("startDate", startDateStringRework);
		obj.put("endDate", endDateStringRework);
//		obj.put("trainingPrice", trainingPrice);
		obj.put("listOfTrainings", dateList);
		obj.put("numberOfTrainings", listTrainings.size());
//		obj.put("oneTrainingPrice", clientTrainingReportDTO.getTrainingPrice());
//		obj.put("numberOfBonusTrainings", clientTrainingReportDTO.getBonusTraining());
		

		 obj.put("successMessage", "Štampanje PDF-a je u toku molimo sačekajte.");
		}

		return ResponseEntity.ok(obj.toString());
	}
	
	@PostMapping(value = { "/trainerTrainingsReportPrint" })
	public ResponseEntity<?> trainerTrainingsReportPrint(@Valid @RequestBody TrainerTrainingReportDataDTO trainerTrainingReportDataDTO) {
		JSONObject obj = new JSONObject();
		int isThereError = -1;
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("name", trainerTrainingReportDataDTO.getName());
		data.put("startDate", trainerTrainingReportDataDTO.getStartDate());
		data.put("endDate", trainerTrainingReportDataDTO.getEndDate());
		
		String[] stringArray = trainerTrainingReportDataDTO.getListOfTrainings().split(",");
		List<String> stringList = new ArrayList<String>();
		for(int k = 0; k < stringArray.length; k++) {
			stringList.add(stringArray[k]);
		}
		
		data.put("listOfTrainings", stringList);//clientTrainingReportDataDTO.getListOfTrainings());
		data.put("numberOfTrainings", trainerTrainingReportDataDTO.getNumberOfTrainings());
		
			try {
				isThereError = pdfGenaratorUtil.clientReportPdf("PDFTemplateTrainerTrainings",data);
				obj.put("successMessage", "Štampanje PDF-a je u toku molimo sačekajte.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return ResponseEntity.ok(obj.toString());
	}
	
	@PostMapping(value = { "/openPDFFolder" })
	public ResponseEntity<?> openPDFFolder() {
		JSONObject obj = new JSONObject();
		System.out.println("Open PDF folder");
		try {
			Runtime.getRuntime().exec("explorer.exe /open," + pdfFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return ResponseEntity.ok(obj.toString());
	}
	
	@PostMapping(value = { "/openLogFile" })
	public ResponseEntity<?> openLogFile() {
		JSONObject obj = new JSONObject();
		System.out.println("Open log folder");
		try {
			Runtime.getRuntime().exec("explorer.exe /open," + loggingPath);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return ResponseEntity.ok(obj.toString());
	}
	
}
