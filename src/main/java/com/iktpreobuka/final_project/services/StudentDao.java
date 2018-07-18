package com.iktpreobuka.final_project.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.final_project.controllers.dto.ChangePasswordDTO;
import com.iktpreobuka.final_project.controllers.dto.StudentDTO;

public interface StudentDao {
	
	public ResponseEntity<?> findAll();
	
	public ResponseEntity<?> getStudentById(String idString);
	
	public ResponseEntity<?> getStudentFromToken();
	
	public ResponseEntity<?> deleteById(String idString);
	
	public ResponseEntity<?> postNewStudent(StudentDTO seBody);
	
	public ResponseEntity<?> putStudentById(StudentDTO seBody, String idString);
	
	public ResponseEntity<?> changePassword(ChangePasswordDTO cpBody,String idString);
	
	
	
	
	
	
	
	
}
