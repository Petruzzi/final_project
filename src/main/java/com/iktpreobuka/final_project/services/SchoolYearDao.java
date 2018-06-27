package com.iktpreobuka.final_project.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.final_project.controllers.dto.SchoolYearDTO;

public interface SchoolYearDao {
	
	public ResponseEntity<?> findAll();
	
	public ResponseEntity<?> getSchoolYearById(String idString);
	
	public ResponseEntity<?> deleteById(String idString);
	
	public ResponseEntity<?> postNewSchoolYear(SchoolYearDTO syeBody);
	
	public ResponseEntity<?> putSchoolYearById(SchoolYearDTO syeBody, String idString);
}
