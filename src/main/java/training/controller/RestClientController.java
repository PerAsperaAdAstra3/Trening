package training.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import training.dto.ClientTrainingReportDTO;
import training.model.Client;
import training.model.ExerciseInRound;
import training.model.Training;
import training.repository.ClientRepository;
import training.repository.TrainingRepository;
import training.util.PdfGenaratorUtil;

@RestController
public class RestClientController {
	
	@Autowired
	private TrainingRepository trainingRepository;
	
	@Autowired
	private PdfGenaratorUtil pdfGenaratorUtil;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@PostMapping(value = { "/clientTrainingsReport"})
	public ResponseEntity<?> clientTrainingsReport(@Valid @RequestBody ClientTrainingReportDTO clientTrainingReportDTO) {
		JSONObject obj = new JSONObject();
		int isThereError = -1;
		String startDateString = clientTrainingReportDTO.getStartDate().toString();
		String endDateString = clientTrainingReportDTO.getEndDate().toString();		
		String[] startDateStringArray = startDateString.split(" ");
		String[] endtDateStringArray = endDateString.split(" ");
		 
		String startDateStringRework = startDateStringArray[2] + " " + startDateStringArray[1] + " " + startDateStringArray[5];
		String endDateStringRework = endtDateStringArray[2] + " " + endtDateStringArray[1] + " " + endtDateStringArray[5];
		
		List<Training> listTrainings = trainingRepository.getForClientInInterval(clientTrainingReportDTO.getHighlightedClientId(), clientTrainingReportDTO.getStartDate(),  clientTrainingReportDTO.getEndDate());
		Map<String, Object> data = new HashMap<String, Object>();
		Client clientInQuestion = clientRepository.findOne(clientTrainingReportDTO.getHighlightedClientId());
		Long trainingPrice = clientTrainingReportDTO.getTrainingPrice() * listTrainings.size();
		
		data.put("name", clientInQuestion.getName() + " "  + clientInQuestion.getFamilyName());
		data.put("startDate", startDateStringRework);
		data.put("endDate", endDateStringRework);
		data.put("trainingPrice", trainingPrice);		
		List<String> dateList = new ArrayList<String>();
		for(Training training : listTrainings) {
			System.out.println(training.getDate());
			String[] iteratedTrainingDate = training.getDate().toString().split(" ");
			String dateWithoutTime = iteratedTrainingDate[0];
			String[] dayMontheYear = dateWithoutTime.split("-");
			dateList.add(dayMontheYear[2] + "." + dayMontheYear[1] + "." + dayMontheYear[0]);
		}
		data.put("listOfTrainings", dateList); //listTrainings);
		data.put("numberOfTrainings", listTrainings.size());		 		 
		try {
			isThereError = pdfGenaratorUtil.clientReportPdf("PDFTemplateClientTrainings",data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(obj.toString());
	}
}
