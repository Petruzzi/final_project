package com.iktpreobuka.final_project.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.final_project.controllers.dto.ScheduleDTO;

public interface ScheduleDao {
	
	public ResponseEntity<?> findAll();
	
	public ResponseEntity<?> getScheduleById(String idString);
	
	public ResponseEntity<?> deleteById(String idString);
	
	public ResponseEntity<?> postNewSchedule(ScheduleDTO seBody);
	
	public ResponseEntity<?> putScheduleById(ScheduleDTO seBody, String idString);
}
