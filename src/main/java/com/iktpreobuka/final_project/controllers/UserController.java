package com.iktpreobuka.final_project.controllers;

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
import com.iktpreobuka.final_project.security.Views;
import com.iktpreobuka.final_project.services.UserDao;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins="http://localhost:4200", allowedHeaders="*")//
//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
public class UserController {
	
	
	@Autowired
	private UserDao userDao;
	
	//Find by token 
	@RequestMapping(method=RequestMethod.GET,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getUserFromToken(){
		return userDao.getUserFromToken();
	}
	
	//Find by  id 
	@RequestMapping(method=RequestMethod.GET,value="/admin/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getUserById(@PathVariable String id){
		return userDao.getUserById(id);
	}
	
	//Find by  id | USERS
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> getUserById_user(@PathVariable String id){
		return userDao.getUserById(id);
	}
	
	//change user password by token || USERS
	@RequestMapping(method=RequestMethod.PUT,value="/change_password/")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO cpe){
		return userDao.changePasswordByToken(cpe);
	}
	
}


















//package com.iktpreobuka.final_project.controllers;
//
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.iktpreobuka.final_project.entities.UserEntity;
//import com.iktpreobuka.final_project.services.UserDao;
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//	@Autowired
//	private UserDao userDao;
//		
//	
//	//Find all
//	@RequestMapping(method=RequestMethod.GET,value="/")
//	public List<UserEntity> getAllUsers(){
//		return (List<UserEntity>)userDao.findAll();
//	}
//	
//	//Find by id
//	@RequestMapping(method=RequestMethod.GET,value="/{id}")
//	public ResponseEntity<?> getUserById(@PathVariable String id){
//		return userDao.getUserById(id);
//	}
//	
//	//Delete by id
//	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
//	public ResponseEntity<?> deleteUser(@PathVariable String id){	
//		return userDao.deleteById(id);	
//	}
//	
//	//Post new user
//	@RequestMapping(method=RequestMethod.POST,value="/")
//	public ResponseEntity<?> postNewUser(@Valid @RequestBody UserEntity se){
//		return userDao.postNewUser(se);
//	}
//	
//	//Put user by id
//	@RequestMapping(method=RequestMethod.PUT,value="/{id}")
//	public ResponseEntity<?> putUserById(@RequestBody UserEntity se,@PathVariable String id){
//		return userDao.putUserById(se,id);
//	}
//}
