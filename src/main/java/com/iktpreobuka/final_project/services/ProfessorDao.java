package com.iktpreobuka.final_project.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.final_project.controllers.dto.ChangePasswordDTO;
import com.iktpreobuka.final_project.controllers.dto.ProfessorDTO;

public interface ProfessorDao {
	
	public ResponseEntity<?> findAll();
	
	public ResponseEntity<?> getProfessorById(String idString);
	
	public ResponseEntity<?> getProfessorFromToken();
	
	public ResponseEntity<?> deleteById(String idString);
	
	public ResponseEntity<?> postNewProfessor(ProfessorDTO peBody);
	
	public ResponseEntity<?> putProfessorById(ProfessorDTO peBody, String idString);
	
	public ResponseEntity<?> changePassword(ChangePasswordDTO cpBody,String idString);

}
