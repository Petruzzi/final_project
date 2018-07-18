package com.iktpreobuka.final_project.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.controllers.dto.MarkDTO;
import com.iktpreobuka.final_project.security.Views;
import com.iktpreobuka.final_project.services.MarkDao;

@RestController
@RequestMapping("/mark")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
public class MarkController {

	@Autowired
	private MarkDao markDao;
	
	//Find all 
	@RequestMapping(method=RequestMethod.GET,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAllMarks(){
		return markDao.findAll();
	}
	
	//Find by id
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getMarkById(@PathVariable String id){
		return markDao.getMarkById(id);
	}
	
	//Find by id || USERS
	@RequestMapping(method=RequestMethod.GET,value="/user/{id}")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> getMarkBy_userId(@PathVariable String id){
		return markDao.getMarkById(id);
	}
		
	//Find by student 
	@RequestMapping(method=RequestMethod.GET,value="/get_by_student_id/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getMarkByStudentId(@PathVariable String id){
		return markDao.getMarkByStudent(id);
	}
	
	//Find by student || PROFESSOR
	@RequestMapping(method=RequestMethod.GET,value="/professor/get_by_student_id/{id}")
	@JsonView(Views.Professor.class)
	public ResponseEntity<?> getMarkByStudentId_professor(@PathVariable String id){
		return markDao.getMarkByStudent(id);
	}
	
	//Find by student || STUDENT
	@RequestMapping(method=RequestMethod.GET,value="/student/{id}")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> getMarkByStudent_student(@PathVariable String id){
		return markDao.getMarkByStudent(id);
	}	
	
	//Find by student || PARENT
	@RequestMapping(method=RequestMethod.GET,value="/parent/get_by_student_id/{id}")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> getMarkByStudentId_parent(@PathVariable String id){
		return markDao.getMarkByStudent(id);
	}

	

	
	//Find by parent | PARENTT
	@RequestMapping(method=RequestMethod.GET,value="/parent/{id}")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> getMarkByParent_parent(@PathVariable String id){
		return markDao.getMarkByParent(id);
	}
	
	//Find by professor | PROFESSOR
	@RequestMapping(method=RequestMethod.GET,value="/professor/{id}")
	@JsonView(Views.Professor.class)
	public ResponseEntity<?>getMarkByProf_professor(@PathVariable String id){
		return markDao.getMarkByProf(id);
	}
	
	//Delete by id
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> deleteMark(@PathVariable String id){	
		return markDao.deleteById(id);	
	}
	
	//Delete by id | PROFESSOR
	@RequestMapping(method=RequestMethod.DELETE,value="/professor/{id}")
	@JsonView(Views.Professor.class)
	public ResponseEntity<?> deleteMark_prof(@PathVariable String id){	
		return markDao.deleteById(id);	
	}
	
	//Post new mark | PROFESSOR
	@RequestMapping(method=RequestMethod.POST,value="/professor/")
	@JsonView(Views.Professor.class)
	public ResponseEntity<?> postNewMark_professor(@Valid @RequestBody MarkDTO me){
		return markDao.postNewMark(me);
	}
	
	//Post new mark 
	@RequestMapping(method=RequestMethod.POST,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> postNewMark(@Valid @RequestBody MarkDTO me){
		return markDao.postNewMark(me);
	}
	
	//Put mark by id | PROFESSOR
	@RequestMapping(method=RequestMethod.PUT,value="/professor/{id}")
	@JsonView(Views.Professor.class)
	public ResponseEntity<?> putMarkById_professor(@RequestBody MarkDTO me,@PathVariable String id){
		return markDao.putMarkById(me,id);
	}
	
	//Put mark by id 
	@RequestMapping(method=RequestMethod.PUT,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> putMarkById(@RequestBody MarkDTO me,@PathVariable String id){
		return markDao.putMarkById(me,id);
	}
	

	//Generate final marks 
	@RequestMapping(method=RequestMethod.POST,value="/generate_final_marks/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> generateFinalMarks(){
		return markDao.generateFinalMarks();
	}
	
	//lock all marks
	@RequestMapping(method=RequestMethod.PUT,value="/lock_marks/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> lockMarks(){
		return markDao.lockMarks();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// nije do kraja implementirano ovo ispod
	
	
	
	
	//uzimanje emaila iz tokena
	@RequestMapping(method=RequestMethod.GET,value="/test1")
	
	public String test1(){
		
		Authentication auth=SecurityContextHolder.getContext().getAuthentication(); 
		String name=auth.getName();
		String lastname=auth.getAuthorities().toString();
		String a=auth.getCredentials().toString();
		String s=auth.getDetails().toString();
		String d=auth.getPrincipal().toString();
		
		return name+" \n"+lastname+" \n"+a+" \n"+s+" \n"+d;
	}
	
	//Test Mail method
//	@RequestMapping(method=RequestMethod.GET,value="/test2/{schedule}/{student}/{semester}")
//	@JsonView(Views.Admin.class)
//	public Boolean test2(@PathVariable Integer schedule, @PathVariable Integer student, @PathVariable Byte semester){
//		
//		
//		return markDao.ifFinalMarkExist(schedule, student, semester);
//	}
	
	


	
}
