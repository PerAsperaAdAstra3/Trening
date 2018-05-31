package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import training.service.TrainingService;

@RestController
@RequestMapping(value = "/api/training")
public class TrainingController {

	@Autowired
	private TrainingService trainingService;
}
