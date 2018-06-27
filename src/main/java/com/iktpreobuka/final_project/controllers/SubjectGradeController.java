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
import com.iktpreobuka.final_project.controllers.dto.SubjectGradeDTO;
import com.iktpreobuka.final_project.security.Views;
import com.iktpreobuka.final_project.services.SubjectGradeDao;

@RestController
@RequestMapping("/subject_grade")
public class SubjectGradeController {
	
	@Autowired
	private SubjectGradeDao subjectGradeDao;
	
	//Find all
	@RequestMapping(method=RequestMethod.GET,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAllSubjectGrades(){
		return subjectGradeDao.findAll();
	}
	
	//Find by id
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getSubjectGradeById(@PathVariable String id){
		return subjectGradeDao.getSubjectGradeById(id);
	}
		
	//Delete by id
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> deleteSubjectGrade(@PathVariable String id){	
		return subjectGradeDao.deleteById(id);	
	}
	
	//Post new subject
	@RequestMapping(method=RequestMethod.POST,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> postNewSubjectGrade(@Valid @RequestBody SubjectGradeDTO sge){
		return subjectGradeDao.postNewSubjectGrade(sge);
	}
	
	//Put subject by id
	@RequestMapping(method=RequestMethod.PUT,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> putSubjectGradeById(@RequestBody SubjectGradeDTO sge,@PathVariable String id){
		return subjectGradeDao.putSubjectGradeById(sge,id);
	}
	
}
