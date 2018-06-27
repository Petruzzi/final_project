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
import com.iktpreobuka.final_project.controllers.dto.SchoolYearDTO;
import com.iktpreobuka.final_project.entities.SchoolYearEntity;
import com.iktpreobuka.final_project.security.Views;
import com.iktpreobuka.final_project.services.SchoolYearDao;

@RestController
@RequestMapping("/school_year")
public class SchoolYearController {

	@Autowired
	private SchoolYearDao schoolYearDao;
		
	
	//Find all
	@RequestMapping(method=RequestMethod.GET,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAllSchoolYears(){
		return schoolYearDao.findAll();
	}
	
	//Find by id
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getSchoolYearById(@PathVariable String id){
		return schoolYearDao.getSchoolYearById(id);
	}
	
	//Delete by id
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> deleteSchoolYear(@PathVariable String id){	
		return schoolYearDao.deleteById(id);	
	}
	
	//Post new school year
	@RequestMapping(method=RequestMethod.POST,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> postNewSchoolYear(@Valid @RequestBody SchoolYearDTO sye){
		return schoolYearDao.postNewSchoolYear(sye);
	}
	
	//Put school year by id
	@RequestMapping(method=RequestMethod.PUT,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> putSchoolYearById(@RequestBody SchoolYearDTO sye,@PathVariable String id){
		return schoolYearDao.putSchoolYearById(sye,id);
	}
}
