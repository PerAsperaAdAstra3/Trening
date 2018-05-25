package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import trening.model.Trening;
import trening.service.TreningService;
import trening.service.ZadatakService;


public class TreningTestData {

	@Autowired
	private TreningService	treningService;
	
	@Autowired
	private ZadatakService zadatakService;
	
	@PostConstruct
	private void init() {
	//	Trening trening1 = new Trening("21-apr-2015", 1, zadatak);
	}
}
