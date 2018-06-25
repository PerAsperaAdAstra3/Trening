package training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import training.converter.RoundToRoundDTO;
import training.dto.RoundDTO;
import training.model.Round;
import training.repository.RoundRepository;

@RestController
@RequestMapping(path="api/rounds")
public class RoundController {

	@Autowired
	RoundRepository roundRepoistory;
	
	@Autowired
	RoundToRoundDTO roundToRoundDTO;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<RoundDTO>> getRounds(){
		List<Round> roundList = roundRepoistory.findAll();
		return new ResponseEntity<>(roundToRoundDTO.convert(roundList), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<RoundDTO> getRound(@PathVariable Long id){
		Round round = roundRepoistory.getOne(id);
		return new ResponseEntity<>(roundToRoundDTO.convert(round), HttpStatus.OK);
	}
	
}
