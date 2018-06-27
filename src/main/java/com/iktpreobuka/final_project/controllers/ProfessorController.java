package com.iktpreobuka.final_project.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.controllers.dto.ChangePasswordDTO;
import com.iktpreobuka.final_project.controllers.dto.ParentDTO;
import com.iktpreobuka.final_project.controllers.dto.ProfessorDTO;
import com.iktpreobuka.final_project.controllers.dto.UserDTO;
import com.iktpreobuka.final_project.security.Views;
import com.iktpreobuka.final_project.services.ProfessorDao;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

	@Autowired
	private ProfessorDao professorDao;
		
	
	//Find all
	@RequestMapping(method=RequestMethod.GET,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAllProfessors(){
		return professorDao.findAll();
	}
	
	//Find by id
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getProfessorById(@PathVariable String id){
		return professorDao.getProfessorById(id);
	}
	
	//Find by id || PROF
	@RequestMapping(method=RequestMethod.GET,value="/professor/{id}")
	@JsonView(Views.Professor.class)
	public ResponseEntity<?> getProfessorById_prof(@PathVariable String id){
		return professorDao.getProfessorById(id);
	}
	
	//Delete by id
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> deleteProfessor(@PathVariable String id){	
		return professorDao.deleteById(id);	
	}
	
	//Post new professor
	@RequestMapping(method=RequestMethod.POST,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> postNewProfessor(@Valid @RequestBody ProfessorDTO se){
		return professorDao.postNewProfessor(se);
	}
	
	//Put professor by id
	@RequestMapping(method=RequestMethod.PUT,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> putProfessorById(@RequestBody ProfessorDTO pe,@PathVariable String id){
		return professorDao.putProfessorById(pe,id);
	}
	
	
	//Put professor by id || PROF
	@RequestMapping(method=RequestMethod.PUT,value="/professor/{id}")
	@JsonView(Views.Professor.class)
	public ResponseEntity<?> putProfessorById_prof(@RequestBody UserDTO userDTO,@PathVariable String id){
		ProfessorDTO pe=new ProfessorDTO();
		pe.setEmail(userDTO.getEmail());
		pe.setUsername(userDTO.getUsername());
		return professorDao.putProfessorById(pe,id);
	}
	
	//change prof  password by id || PROF
	@RequestMapping(method=RequestMethod.PUT,value="/professor/change_password/{id}")
	@JsonView(Views.Professor.class)
	public ResponseEntity<?> changePassword_prof(@RequestBody ChangePasswordDTO cpe,@PathVariable String id){
		return professorDao.changePassword(cpe,id);
	}
	
	
	
	
	
}
