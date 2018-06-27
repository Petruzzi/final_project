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
import com.iktpreobuka.final_project.entities.SubjectEntity;
import com.iktpreobuka.final_project.security.Views;
import com.iktpreobuka.final_project.services.SubjectDao;

@RestController
@RequestMapping("/subject")
public class SubjectController {

	@Autowired
	private SubjectDao subjectDao;
		
	
	//Find all
	@RequestMapping(method=RequestMethod.GET,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAllSubjects(){
		return subjectDao.findAll();
	}
	
	//Find by id
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getSubjectById(@PathVariable String id){
		return subjectDao.getSubjectById(id);
	}
	
	//Delete by id
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> deleteSubject(@PathVariable String id){	
		return subjectDao.deleteById(id);	
	}
	
	//Post new subject
	@RequestMapping(method=RequestMethod.POST,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> postNewSubject(@Valid @RequestBody SubjectEntity se){
		return subjectDao.postNewSubject(se);
	}
	
	//Put subject by id
	@RequestMapping(method=RequestMethod.PUT,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> putSubjectById(@Valid @RequestBody SubjectEntity se,@PathVariable String id){
		return subjectDao.putSubjectById(se,id);
	}
}
