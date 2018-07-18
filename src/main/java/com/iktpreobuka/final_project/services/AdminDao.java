package com.iktpreobuka.final_project.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.final_project.controllers.dto.ChangePasswordDTO;
import com.iktpreobuka.final_project.controllers.dto.UserDTO;

public interface AdminDao {
	
	public ResponseEntity<?> findAll();
	
	public ResponseEntity<?> getAdminById(String idString);
	
	public ResponseEntity<?> getAdminFromToken();
	
	public ResponseEntity<?> deleteById(String idString);
	
	public ResponseEntity<?> postNewAdmin(UserDTO aeBody);
	
	public ResponseEntity<?> putAdminById(UserDTO aeBody, String idString);
	
	public ResponseEntity<?> changePassword(ChangePasswordDTO cpBody,String idString);
	
	public ResponseEntity<?> resetUserPassword(String idString);
	
	public ResponseEntity<?> getLog();
}
