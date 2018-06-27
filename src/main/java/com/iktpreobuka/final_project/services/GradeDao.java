package com.iktpreobuka.final_project.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.final_project.entities.GradeEntity;

public interface GradeDao {
	
	public ResponseEntity<?> findAll();
	
	public ResponseEntity<?> getGradeById(String idString);
	
	public ResponseEntity<?> deleteById(String idString);
	
	public ResponseEntity<?> postNewGrade(GradeEntity geBody);
	
	public ResponseEntity<?> putGradeById(GradeEntity geBody, String idString);
}
