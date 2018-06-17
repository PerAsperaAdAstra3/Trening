package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import training.service.TrainingService;

@RestController
@RequestMapping(value = "/api/training")
public class TrainingController {

	@Autowired
	private TrainingService trainingService;

	@RequestMapping(value = "testing", method = RequestMethod.GET)
	public ResponseEntity<String> testFunkcionalno(){
		String testS = "TestingTesting";
		return new ResponseEntity<>(testS, HttpStatus.OK);
	}
}
