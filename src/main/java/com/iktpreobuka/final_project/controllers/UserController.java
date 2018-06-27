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
