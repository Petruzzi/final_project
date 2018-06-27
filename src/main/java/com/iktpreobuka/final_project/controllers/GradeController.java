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
import com.iktpreobuka.final_project.entities.GradeEntity;
import com.iktpreobuka.final_project.security.Views;
import com.iktpreobuka.final_project.services.GradeDao;

@RestController
@RequestMapping("/grade")
public class GradeController {

	@Autowired
	private GradeDao gradeDao;
		
	
	//Find all
	@RequestMapping(method=RequestMethod.GET,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAllGrades(){
		return gradeDao.findAll();
	}
	
	//Find by id
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getGradeById(@PathVariable String id){
		return gradeDao.getGradeById(id);
	}
	
	//Delete by id
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> deleteGrade(@PathVariable String id){	
		return gradeDao.deleteById(id);	
	}
	
	//Post new grade
	@RequestMapping(method=RequestMethod.POST,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> postNewGrade(@Valid @RequestBody GradeEntity ge){
		return gradeDao.postNewGrade(ge);
	}
	
	//Put grade by id
	@RequestMapping(method=RequestMethod.PUT,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> putGradeById(@RequestBody GradeEntity ge,@PathVariable String id){
		return gradeDao.putGradeById(ge,id);
	}
}
