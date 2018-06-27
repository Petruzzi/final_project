package com.iktpreobuka.final_project.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.final_project.controllers.dto.SubjectGradeDTO;

public interface SubjectGradeDao {
	
	public ResponseEntity<?> findAll();
	
	public ResponseEntity<?> getSubjectGradeById(String idString);
	
	public ResponseEntity<?> deleteById(String idString);
	
	public ResponseEntity<?> postNewSubjectGrade(SubjectGradeDTO sgeBody);
	
	public ResponseEntity<?> putSubjectGradeById(SubjectGradeDTO sgeBody, String idString);
}
