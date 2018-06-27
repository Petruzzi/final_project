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
import com.iktpreobuka.final_project.controllers.dto.ScheduleDTO;
import com.iktpreobuka.final_project.security.Views;
import com.iktpreobuka.final_project.services.ScheduleDao;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	private ScheduleDao scheduleDao;
		
	
	//Find all
	@RequestMapping(method=RequestMethod.GET,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAllSchedules(){
		return scheduleDao.findAll();
	}
	
	//Find by id
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getScheduleById(@PathVariable String id){
		return scheduleDao.getScheduleById(id);
	}
	
	//Delete by id
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> deleteSchedule(@PathVariable String id){	
		return scheduleDao.deleteById(id);	
	}
	
	//Post new schedule
	@RequestMapping(method=RequestMethod.POST,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> postNewSchedule(@Valid @RequestBody ScheduleDTO se){
		return scheduleDao.postNewSchedule(se);
	}
	
	//Put schedule by id
	@RequestMapping(method=RequestMethod.PUT,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> putScheduleById(@RequestBody ScheduleDTO se,@PathVariable String id){
		return scheduleDao.putScheduleById(se,id);
	}
}
