package training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import training.converter.TrainingToTrainingDTO;
import training.dto.TrainingDTO;
import training.model.Training;
import training.service.TrainingService;

@RestController
@RequestMapping(value = "/api/trainings")
public class TrainingController {

	@Autowired
	private TrainingService trainingService;

	@Autowired
	private TrainingToTrainingDTO trainingToTrainingDTO;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<TrainingDTO>> findAll(){
		List<Training> trainings = trainingService.findAll();
		return new ResponseEntity<>(trainingToTrainingDTO.convert(trainings) , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}" ,method = RequestMethod.GET)
	public ResponseEntity<TrainingDTO> findOne(@PathVariable Long id){
		Training training = trainingService.findOne(id);
		return new ResponseEntity<>(trainingToTrainingDTO.convert(training), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<TrainingDTO> add(@RequestBody Training training){
		trainingService.save(training);	
		return new ResponseEntity<>(trainingToTrainingDTO.convert(training), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<TrainingDTO> delete(@RequestBody Training training){
		trainingService.save(training);
		return new ResponseEntity<>(trainingToTrainingDTO.convert(training), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}" , method = RequestMethod.POST)
	public ResponseEntity<TrainingDTO> edit(@PathVariable Long id, @RequestBody Training training){
		Training newTraining = trainingService.edit(id, training);
		return new ResponseEntity<>(trainingToTrainingDTO.convert(newTraining) , HttpStatus.OK);
	}
}
