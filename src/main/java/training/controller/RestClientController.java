package training.controller;

import java.util.ArrayList;
import java.util.Date;
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
import training.dto.ClientTrainingReportDataDTO;
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
		if(clientTrainingReportDTO.getBonusTraining() == null) {
			clientTrainingReportDTO.setBonusTraining(0l);
		}
		
		if(clientTrainingReportDTO.getEndDate() == null) {
			clientTrainingReportDTO.setEndDate(new Date()); 
		}
		
		if(clientTrainingReportDTO.getStartDate() == null) {
			clientTrainingReportDTO.setStartDate(clientTrainingReportDTO.getEndDate());
		}
		
		if(clientTrainingReportDTO.getTrainingPrice() == null) {
			clientTrainingReportDTO.setTrainingPrice(0l);
		}
		
		if(clientTrainingReportDTO.getStartDate() != null && clientTrainingReportDTO.getEndDate() != null && clientTrainingReportDTO.getTrainingPrice() != null && clientTrainingReportDTO.getBonusTraining() != null && clientTrainingReportDTO.getHighlightedClientId() != null) {

		String startDateString = clientTrainingReportDTO.getStartDate().toString();
		String endDateString = clientTrainingReportDTO.getEndDate().toString();		
		String[] startDateStringArray = startDateString.split(" ");
		String[] endtDateStringArray = endDateString.split(" ");
		 
		String startDateStringRework = startDateStringArray[2] + " " + startDateStringArray[1] + " " + startDateStringArray[5];
		String endDateStringRework = endtDateStringArray[2] + " " + endtDateStringArray[1] + " " + endtDateStringArray[5];
		
		List<Training> listTrainings = trainingRepository.getForClientInInterval(clientTrainingReportDTO.getHighlightedClientId(), clientTrainingReportDTO.getStartDate(),  clientTrainingReportDTO.getEndDate());
		Map<String, Object> data = new HashMap<String, Object>();
		Client clientInQuestion = clientRepository.findById(clientTrainingReportDTO.getHighlightedClientId()).get();
		Long trainingPrice = clientTrainingReportDTO.getTrainingPrice() * (listTrainings.size() - clientTrainingReportDTO.getBonusTraining());
		if(trainingPrice < 0) {
			trainingPrice = 0l;
		}
		data.put("name", clientInQuestion.getName() + " "  + clientInQuestion.getFamilyName());
		data.put("startDate", startDateStringRework);
		data.put("endDate", endDateStringRework);
		data.put("trainingPrice", trainingPrice);		
		//List<String> dateList = new ArrayList<String>();
		String dateList = "";
		for(Training training : listTrainings) {
			String[] iteratedTrainingDate = training.getDate().toString().split(" ");
			String dateWithoutTime = iteratedTrainingDate[0];
			String[] dayMontheYear = dateWithoutTime.split("-");
			//dateList.add(dayMontheYear[2] + "." + dayMontheYear[1] + "." + dayMontheYear[0]);
			dateList += dayMontheYear[2] + "." + dayMontheYear[1] + "." + dayMontheYear[0]+",";
		}
		data.put("listOfTrainings", dateList); //listTrainings);
		data.put("numberOfTrainings", listTrainings.size());
		data.put("oneTrainingPrice", clientTrainingReportDTO.getTrainingPrice());		
		data.put("numberOfBonusTrainings", clientTrainingReportDTO.getBonusTraining());
		//
		obj.put("name", clientInQuestion.getName() + " "  + clientInQuestion.getFamilyName());
		obj.put("startDate", startDateStringRework);
		obj.put("endDate", endDateStringRework);
		obj.put("trainingPrice", trainingPrice);
		obj.put("listOfTrainings", dateList);
		obj.put("numberOfTrainings", listTrainings.size());
		obj.put("oneTrainingPrice", clientTrainingReportDTO.getTrainingPrice());
		obj.put("numberOfBonusTrainings", clientTrainingReportDTO.getBonusTraining());
		//
	    if(listTrainings.size() == 0) {
	    	obj.put("successMessage", "Klijent još nema urađenih treninga!");
			return ResponseEntity.ok(obj.toString());
		}
	    /*	try {
				isThereError = pdfGenaratorUtil.clientReportPdf("PDFTemplateClientTrainings",data);
				obj.put("successMessage", "Štampanje PDF-a je u toku molimo sačekajte.");
			} catch (Exception e) {
				e.printStackTrace();
			}*/
	    obj.put("successMessage", "Štampanje PDF-a je u toku molimo sačekajte.");
		}
		return ResponseEntity.ok(obj.toString());
	}
	
	@PostMapping(value = { "/clientTrainingsReportPrint"})
	public ResponseEntity<?> clientTrainingsReportPrint(@Valid @RequestBody ClientTrainingReportDataDTO clientTrainingReportDataDTO) {
		JSONObject obj = new JSONObject();
		int isThereError = -1;
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("name", clientTrainingReportDataDTO.getName());
		data.put("startDate", clientTrainingReportDataDTO.getStartDate());
		data.put("endDate", clientTrainingReportDataDTO.getEndDate());
		data.put("trainingPrice", clientTrainingReportDataDTO.getTrainingPrice());	
		
		String[] stringArray = clientTrainingReportDataDTO.getListOfTrainings().split(",");
		List<String> stringList = new ArrayList<String>();
		for(int k = 0; k < stringArray.length; k++) {
			stringList.add(stringArray[k]);
		}
		
		data.put("listOfTrainings", stringList);//clientTrainingReportDataDTO.getListOfTrainings());
		data.put("numberOfTrainings", clientTrainingReportDataDTO.getNumberOfTrainings());
		data.put("oneTrainingPrice", clientTrainingReportDataDTO.getOneTrainingPrice());		
		data.put("numberOfBonusTrainings", clientTrainingReportDataDTO.getNumberOfBonusTrainings());
		
	    	try {
				isThereError = pdfGenaratorUtil.clientReportPdf("PDFTemplateClientTrainings",data);
				obj.put("successMessage", "Štampanje PDF-a je u toku molimo sačekajte.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return ResponseEntity.ok(obj.toString());
	}
}
