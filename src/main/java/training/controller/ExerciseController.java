package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import training.model.Exercise;
import training.service.ExerciseService;

@RestController
@RequestMapping(path="api/exercise")
public class ExerciseController {

	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private Exercise exercise;
	
	
	
}
