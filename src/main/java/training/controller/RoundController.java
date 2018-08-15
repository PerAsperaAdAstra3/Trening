package training.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import training.converter.RoundToRoundDTO;
import training.dto.RoundDTO;
import training.model.Round;
import training.service.RoundService;

@RestController
@RequestMapping(path="api/rounds")
public class RoundController {

	@Autowired
	RoundService roundService;
	
	@Autowired
	RoundToRoundDTO roundToRoundDTO;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<RoundDTO>> getRounds(){
		List<Round> roundList = roundService.findAll();
		return new ResponseEntity<>(roundToRoundDTO.convert(roundList), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<RoundDTO> getRound(@PathVariable Long id){
		Round round = roundService.findOne(id);
		return new ResponseEntity<>(roundToRoundDTO.convert(round), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST )
	public ResponseEntity<?> addRound(@Valid @RequestBody Round round, Errors errors){
		if(errors.hasErrors()) {
			return new ResponseEntity<String>(errors.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
		}
		Round newRound = roundService.save(round);
		return new ResponseEntity<>(roundToRoundDTO.convert(newRound), HttpStatus.OK);
		}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)	
	public ResponseEntity<RoundDTO> delete(@PathVariable Long id){
		Round round = roundService.delete(id);
		return new ResponseEntity<RoundDTO>(roundToRoundDTO.convert(round), HttpStatus.OK);
		}

	@RequestMapping()
	public ResponseEntity<RoundDTO> edit(@PathVariable Long id, @RequestBody Round round){
		Round newRound = roundService.edit(id, round);
		return new ResponseEntity<>(roundToRoundDTO.convert(newRound), HttpStatus.OK) ;
		}
}
