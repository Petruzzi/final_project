package com.iktpreobuka.final_project.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.final_project.controllers.dto.MarkDTO;

public interface MarkDao {
	
	public ResponseEntity<?> findAll();
	
	public ResponseEntity<?> getMarkById(String idString);
	
	public ResponseEntity<?> deleteById(String idString);
	
	public ResponseEntity<?> postNewMark(MarkDTO meBody);
	
	public ResponseEntity<?> putMarkById(MarkDTO meBody, String idString);
	
	public ResponseEntity<?> getMarkByStudent(String idString);
	
	public ResponseEntity<?> getMarkByParent(String idString);
	
	public ResponseEntity<?> getMarkByProf(String idString);
	
	//public Boolean ifFinalMarkExist(Integer schedule, Integer student , Byte semester);
	
	public ResponseEntity<?> generateFinalMarks();
	
	public ResponseEntity<?> lockMarks();
}
