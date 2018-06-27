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
import com.iktpreobuka.final_project.entities.AbsenceRecordEntity;
import com.iktpreobuka.final_project.security.Views;
import com.iktpreobuka.final_project.services.AbsenceRecordDao;

@RestController
@RequestMapping("/absence_record")
public class AbsenceRecordController {

	@Autowired
	private AbsenceRecordDao absenceRecordDao;
		
	
	//Find all
	@RequestMapping(method=RequestMethod.GET,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAllAbsenceRecords(){
		return absenceRecordDao.findAll();
	}
	
	//Find by id
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAbsenceRecordById(@PathVariable String id){
		return absenceRecordDao.getAbsenceRecordById(id);
	}
	
	//Delete by id
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> deleteAbsenceRecord(@PathVariable String id){	
		return absenceRecordDao.deleteById(id);	
	}
	
	//Post new absence record
	@RequestMapping(method=RequestMethod.POST,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> postNewAbsenceRecord(@Valid @RequestBody AbsenceRecordEntity are){
		return absenceRecordDao.postNewAbsenceRecord(are);
	}
	
	//Put grade by id
	@RequestMapping(method=RequestMethod.PUT,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> putAbsenceRecordById(@RequestBody AbsenceRecordEntity are,@PathVariable String id){
		return absenceRecordDao.putAbsenceRecordById(are,id);
	}
}
