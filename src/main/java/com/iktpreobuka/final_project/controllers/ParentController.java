package com.iktpreobuka.final_project.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.controllers.dto.ChangePasswordDTO;
import com.iktpreobuka.final_project.controllers.dto.ParentDTO;
import com.iktpreobuka.final_project.controllers.dto.UserDTO;
import com.iktpreobuka.final_project.security.Views;
import com.iktpreobuka.final_project.services.ParentDao;

@RestController
@RequestMapping("/parent")
//@CrossOrigin(origins="http://localhost:4200", allowedHeaders="*")//
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
public class ParentController {
	
	@Autowired
	private ParentDao parentDao;

	
	
	//Find all
	@RequestMapping(method=RequestMethod.GET,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAllParents(){
		return parentDao.findAll();
	}
	
	//Find by id 
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getParentByIdAdmin(@PathVariable String id){
		return parentDao.getParentById(id);
	}
	
	//Find by id || PARENT
	@RequestMapping(method=RequestMethod.GET,value="/parent/{id}")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> getParentById_parent(@PathVariable String id){
		return parentDao.getParentById(id);
	}
	
	//Find by token ||PARENT
	@RequestMapping(method=RequestMethod.GET,value="/parent/")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> getParentFromToken_parent(){
		return parentDao.getParentFromToken();
	}
	
	//Find all students by parent token ||PARENT
	@RequestMapping(method=RequestMethod.GET,value="/parent/find_students/")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> getStudentsFromParentToken_parent(){
		return parentDao.getStudentsFromParentToken();
	}
	
	//Delete by id
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> deleteParent(@PathVariable String id){	
		return parentDao.deleteById(id);	
	}
	
	//Post new parent
	@RequestMapping(method=RequestMethod.POST,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> postNewParent(@Valid @RequestBody ParentDTO se){
		return parentDao.postNewParent(se);
	}
	
	//Put parent by id
	@RequestMapping(method=RequestMethod.PUT,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> putParentById(@RequestBody ParentDTO se,@PathVariable String id){
		return parentDao.putParentById(se,id);
	}
	
	//Put parent by id || PARENT
	@RequestMapping(method=RequestMethod.PUT,value="/parent/{id}")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> putParentById_parent(@RequestBody UserDTO userDTO,@PathVariable String id){
		ParentDTO pe=new ParentDTO();
		pe.setEmail(userDTO.getEmail());
		pe.setUsername(userDTO.getUsername());
		return parentDao.putParentById(pe,id);
	}
	
	//change parent  password by id || PARENT
	@RequestMapping(method=RequestMethod.PUT,value="/parent/change_password/{id}")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> changePassword_parent(@RequestBody ChangePasswordDTO cpe,@PathVariable String id){
		return parentDao.changePassword(cpe,id);
	}

}
