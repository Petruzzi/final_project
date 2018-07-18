package com.iktpreobuka.final_project.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.final_project.controllers.dto.ChangePasswordDTO;
import com.iktpreobuka.final_project.controllers.dto.ParentDTO;

public interface ParentDao {
	
	public ResponseEntity<?> findAll();
	
	public ResponseEntity<?> getParentById(String idString);
	
	public ResponseEntity<?> getParentFromToken();
	
	public ResponseEntity<?> getStudentsFromParentToken();
	
	public ResponseEntity<?> deleteById(String idString);
	
	public ResponseEntity<?> postNewParent(ParentDTO peBody);
	
	public ResponseEntity<?> putParentById(ParentDTO peBody, String idString);
	
	public ResponseEntity<?> changePassword(ChangePasswordDTO cpBody,String idString);
	
	
}
