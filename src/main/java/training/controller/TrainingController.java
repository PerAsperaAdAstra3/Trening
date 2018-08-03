package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import training.converter.ExerciseDTOtoExercise;
import training.converter.ExerciseGroupToExerciseGroupDTO;
import training.converter.ExerciseToExerciseDTO;
import training.converter.TrainingToTrainingDTO;
import training.dto.ExerciseDTO;
import training.dto.TrainingDTO;
import training.model.Training;
import training.service.ExerciseGroupService;
import training.service.ExerciseService;
import training.service.TrainingService;

@Controller
public class TrainingController {

	@Autowired
	private ExerciseToExerciseDTO exerciseToExerciseDTO;
	
	@Autowired
	private ExerciseDTOtoExercise exerciseDTOtoExercise;
	
	@Autowired
	private ExerciseGroupService exerciseGroupService;
	
	@Autowired
	private ExerciseGroupToExerciseGroupDTO exerciseGroupToExerciseDTO;
	
	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private TrainingService trainingService;

	@Autowired
	private TrainingToTrainingDTO trainingToTrainingDTO;
	
	@RequestMapping(value = {"/trainingList"}, method = RequestMethod.GET)
	public String getTrainings(Model model){
		model.addAttribute("trainingDTOSearch", new TrainingDTO());
		model.addAttribute("trainingDTO", new TrainingDTO());
		model.addAttribute("trainings", trainingToTrainingDTO.convert(trainingService.findAll()));
		System.out.println("Client list je tu");
		return "training";
	}
	
	@RequestMapping(value = {"/trainingCreationHandler"}, method = RequestMethod.GET)
	public String createTraining(Model model){
		System.out.println("FUUUUUUCK YOU!!!!");
		model.addAttribute("exerciseDTO", new ExerciseDTO());
		model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
		model.addAttribute("exerciseGroups", exerciseGroupToExerciseDTO.convert(exerciseGroupService.findAll()));
		model.addAttribute("exercises", exerciseToExerciseDTO.convert(exerciseService.findAll()));
		model.addAttribute("hiddenExerciseGroupId", "") ;
		return "trainingCreation";
	}
	/*
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
	}*/
}
