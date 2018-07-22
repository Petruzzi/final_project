package com.iktpreobuka.final_project.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.final_project.controllers.dto.ChangePasswordDTO;
import com.iktpreobuka.final_project.entities.UserEntity;

public interface UserDao {
	
//	public ResponseEntity<?> findAll();
//	
//	public ResponseEntity<?> getUserById(String idString);
//	
//	public ResponseEntity<?> deleteById(String idString);
//	
//	public ResponseEntity<?> postNewUser(UserEntity ueBody);
//	
//	public ResponseEntity<?> putUserById(UserEntity ueBody, String idString);
	
	public ResponseEntity<?> changePassword(ChangePasswordDTO cpBody,String idString);
	
	public ResponseEntity<?> changePasswordByToken(ChangePasswordDTO cpBody);
	
	public ResponseEntity<?> resetUserPassword(String idString);
	
	public ResponseEntity<?> getUserFromToken();
	
	public ResponseEntity<?> getUserById(String id);
	
	public Boolean checkEmail(String str);
	
}
