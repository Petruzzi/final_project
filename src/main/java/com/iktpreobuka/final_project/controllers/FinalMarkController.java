package com.iktpreobuka.final_project.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.controllers.dto.MarkDTO;
import com.iktpreobuka.final_project.security.Views;
import com.iktpreobuka.final_project.services.FinalMarkDao;

@RestController
@RequestMapping("/final_mark")
public class FinalMarkController {

	@Autowired
	private FinalMarkDao finalMarkDao;
		
	
	//Find all
	@RequestMapping(method=RequestMethod.GET,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAllFinalMarks(){
		return finalMarkDao.findAll();
	}
	
	//Find by id
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getFinalMarkById(@PathVariable String id){
		return finalMarkDao.getFinalMarkById(id);
	}
	
	//Delete by id
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> deleteFinalMark(@PathVariable String id){	
		return finalMarkDao.deleteById(id);	
	}
	
	//Post new final mark 
	@RequestMapping(method=RequestMethod.POST,value="/professor/")
	@JsonView(Views.Professor.class)
	public ResponseEntity<?> postNewFinalMark_professor(@Valid @RequestBody MarkDTO fme){
		return finalMarkDao.postNewFinalMark(fme);
	}
	
	//Put final mark by id
	@RequestMapping(method=RequestMethod.PUT,value="/professor/{id}")
	@JsonView(Views.Professor.class)
	public ResponseEntity<?> putFinalMarkById_professor(@RequestBody MarkDTO fme,@PathVariable String id){
		return finalMarkDao.putFinalMarkById(fme,id);
	}
	
}
