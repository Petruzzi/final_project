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
import com.iktpreobuka.final_project.controllers.dto.ClassDTO;
import com.iktpreobuka.final_project.security.Views;
import com.iktpreobuka.final_project.services.ClassDao;

@RestController
@RequestMapping("/class")
public class ClassController {

	@Autowired
	private ClassDao classDao;
		
	
	//Find all
	@RequestMapping(method=RequestMethod.GET,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAllClasses(){
		return classDao.findAll();
	}
	
	//Find by id
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getClassById(@PathVariable String id){
		return classDao.getClassById(id);
	}
	
	//Find by id | USER
	@RequestMapping(method=RequestMethod.GET,value="/user/{id}")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> getClassById_user(@PathVariable String id){
		return classDao.getClassById(id);
	}
	
	//Delete by id
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> deleteClass(@PathVariable String id){	
		return classDao.deleteById(id);	
	}
	
	//Post new class
	@RequestMapping(method=RequestMethod.POST,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> postNewClass(@Valid @RequestBody ClassDTO ce){
		return classDao.postNewClass(ce);
	}
	
	//Put class by id
	@RequestMapping(method=RequestMethod.PUT,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> putClassById(@RequestBody ClassDTO ce,@PathVariable String id){
		return classDao.putClassById(ce,id);
	}

	//Get all class by prof | PROFESSOR
	@RequestMapping(method=RequestMethod.GET,value="/professor/by_prof/{id}")
	@JsonView(Views.Professor.class)
	public ResponseEntity<?> getClassByProf_professor(@PathVariable String id){
		return classDao.getClassByProf(id);
	}
	
	//Get all class by prof | ADMIN
	@RequestMapping(method=RequestMethod.GET,value="/by_prof/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getClassByProf_admin(@PathVariable String id){
		return classDao.getClassByProf(id);
	}

	//Get class by head teacher | PROFESSOR
	@RequestMapping(method=RequestMethod.GET,value="/professor/by_head_teacher/{id}")
	@JsonView(Views.Professor.class)
	public ResponseEntity<?> putClassByHeadTeacher_professor(@PathVariable String id){
		return classDao.getClassByHeadTeacher(id);
	}
	
}
