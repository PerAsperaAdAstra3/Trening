package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import training.service.TaskService;
import training.service.TrainingService;


public class TrainingTestData {

	@Autowired
	private TrainingService	trainingService;
	
	@Autowired
	private TaskService taskService;
	
	@PostConstruct
	private void init() {
	//	Training training1 = new Training("21-apr-2015", 1, task);
	}
}
