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

import training.converter.ExerciseGroupToExerciseGroupDTO;
import training.dto.ExerciseGroupDTO;
import training.model.ExerciseGroup;
import training.service.ExerciseGroupService;

@RestController
@RequestMapping(path = "/api/exerciseGroup")
public class ExerciseGroupController {
	
	@Autowired
	private ExerciseGroupService exerciseGroupService;
	
	@Autowired
	private ExerciseGroupToExerciseGroupDTO exerciseGroupToExerciseGroupDTO;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ExerciseGroupDTO>> findAll(){
		List<ExerciseGroup> exerciseGroupList = exerciseGroupService.findAll();
		return new ResponseEntity<>(exerciseGroupToExerciseGroupDTO.convert(exerciseGroupList), HttpStatus.OK);
	} 
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ExerciseGroupDTO> findOne(@PathVariable Long id ){
		ExerciseGroup exerciseGroup = exerciseGroupService.findOne(id);
		return new ResponseEntity<>( exerciseGroupToExerciseGroupDTO.convert(exerciseGroup), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<ExerciseGroupDTO> add(@RequestBody ExerciseGroup exerciseGroup){
		exerciseGroupService.save(exerciseGroup);
		return new ResponseEntity<ExerciseGroupDTO>(exerciseGroupToExerciseGroupDTO.convert(exerciseGroup), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ExerciseGroupDTO> delete(@PathVariable Long id ){
		ExerciseGroup exerciseGroup = exerciseGroupService.delete(id);
		return new ResponseEntity<>(exerciseGroupToExerciseGroupDTO.convert(exerciseGroup), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<ExerciseGroupDTO> edit(@PathVariable Long id, @RequestBody ExerciseGroup exerciseGroup){
		ExerciseGroup editedExerciseGroup = exerciseGroupService.edit(id, exerciseGroup);
		return new ResponseEntity<>(exerciseGroupToExerciseGroupDTO.convert(editedExerciseGroup), HttpStatus.OK) ;
	}
}
