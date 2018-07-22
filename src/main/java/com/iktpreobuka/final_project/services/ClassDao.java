package com.iktpreobuka.final_project.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.final_project.controllers.dto.ClassDTO;

public interface ClassDao {
	
	public ResponseEntity<?> findAll();
	
	public ResponseEntity<?> getClassById(String idString);
	
	public ResponseEntity<?> getClassByStudentId(String studId);
	
	public ResponseEntity<?> deleteById(String idString);
	
	public ResponseEntity<?> postNewClass(ClassDTO ceBody);
	
	public ResponseEntity<?> putClassById(ClassDTO ceBody, String idString);
	
	public ResponseEntity<?> getClassByProf(String profId);
	
	public ResponseEntity<?> getClassByHeadTeacher(String headTeacherId);
}
