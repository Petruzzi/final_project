package com.iktpreobuka.final_project.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.final_project.controllers.dto.MarkDTO;

public interface FinalMarkDao {
	
	public ResponseEntity<?> findAll();
	
	public ResponseEntity<?> getFinalMarkById(String idString);
	
	public ResponseEntity<?> deleteById(String idString);
	
	public ResponseEntity<?> postNewFinalMark(MarkDTO fmeBody);
	
	public ResponseEntity<?> putFinalMarkById(MarkDTO fmeBody, String idString);
}
