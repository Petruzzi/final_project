package com.iktpreobuka.final_project.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.final_project.entities.SubjectEntity;

public interface SubjectDao {
	
	public ResponseEntity<?> findAll();
	
	public ResponseEntity<?> getSubjectById(String idString);
	
	public ResponseEntity<?> deleteById(String idString);
	
	public ResponseEntity<?> postNewSubject(SubjectEntity seBody);
	
	public ResponseEntity<?> putSubjectById(SubjectEntity seBody, String idString);
}
