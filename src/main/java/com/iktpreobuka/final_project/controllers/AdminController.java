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
import com.iktpreobuka.final_project.controllers.dto.UserDTO;
import com.iktpreobuka.final_project.security.Views;
import com.iktpreobuka.final_project.services.AdminDao;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminDao adminDao;
		
	//Find all
	@RequestMapping(method=RequestMethod.GET,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAllAdmin(){
		return adminDao.findAll();
	}
	
	//Find by id
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAdminById(@PathVariable String id){
		return adminDao.getAdminById(id);
	}
	
	//Delete by id
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> deleteAdmin(@PathVariable String id){	
		return adminDao.deleteById(id);	
	}
	
	//Post new admin
	@RequestMapping(method=RequestMethod.POST,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> postNewAdmin(@Valid @RequestBody UserDTO ae){
		return adminDao.postNewAdmin(ae);
	}
	//Put admin by id
	@RequestMapping(method=RequestMethod.PUT,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> putAdminById(@RequestBody UserDTO ae,@PathVariable String id){
		return adminDao.putAdminById(ae,id);
	}
	
	//change admin  password by id
	@RequestMapping(method=RequestMethod.PUT,value="/change_password/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO cpe,@PathVariable String id){
		return adminDao.changePassword(cpe,id);
	}
	
	//change admin  password by id
	@RequestMapping(method=RequestMethod.PUT,value="/reset_password/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> resetUserPassword(@RequestBody ChangePasswordDTO cpe,@PathVariable String id){
		return adminDao.resetUserPassword(id);
	}
	
}
