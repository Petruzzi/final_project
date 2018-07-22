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
import com.iktpreobuka.final_project.controllers.dto.StudentDTO;
import com.iktpreobuka.final_project.controllers.dto.UserDTO;
import com.iktpreobuka.final_project.security.Views;
import com.iktpreobuka.final_project.services.StudentDao;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentDao studentDao;
		
	
	//Find all
	@RequestMapping(method=RequestMethod.GET,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getAllStudents(){
		return studentDao.findAll();
	}
	
	//Find by id
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> getStudentById(@PathVariable String id){
		return studentDao.getStudentById(id);
	}
	
	//Find by id ||STUDENT
	@RequestMapping(method=RequestMethod.GET,value="/student/{id}")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> getStudentById_student(@PathVariable String id){
		return studentDao.getStudentById(id);
	}
	
	//Find by token ||STUDENT
	@RequestMapping(method=RequestMethod.GET,value="/student/")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> getStudentFromToken_student(){
		return studentDao.getStudentFromToken();
	}
	
	//Delete by id
	@RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> deleteStudent(@PathVariable String id){	
		return studentDao.deleteById(id);	
	}
	
	//Post new student
	@RequestMapping(method=RequestMethod.POST,value="/")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> postNewStudent(@Valid @RequestBody StudentDTO se){
		return studentDao.postNewStudent(se);
	}
	
	//Put student by id
	@RequestMapping(method=RequestMethod.PUT,value="/{id}")
	@JsonView(Views.Admin.class)
	public ResponseEntity<?> putStudentById(@RequestBody StudentDTO se,@PathVariable String id){
		return studentDao.putStudentById(se,id);
	}

	//Put student by id ||STUDENT
	@RequestMapping(method=RequestMethod.PUT,value="/student/{id}")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> putStudentById_student(@RequestBody UserDTO userDTO,@PathVariable String id){
		StudentDTO se=new StudentDTO();
		se.setEmail(userDTO.getEmail());
		se.setUsername(userDTO.getUsername());
		return studentDao.putStudentById(se,id);
	}
	
	
	//change student password by id || STUDENT
	@RequestMapping(method=RequestMethod.PUT,value="/student/change_password/{id}")
	@JsonView(Views.Private.class)
	public ResponseEntity<?> changePassword_student(@RequestBody ChangePasswordDTO cpe,@PathVariable String id){
		return studentDao.changePassword(cpe,id);
	}
	
	
//	//cTEST
//	@RequestMapping(method=RequestMethod.PUT,value="/test/")
//	@JsonView(Views.Private.class)
//	public void test(){
//		
//		
//		
//		
//		
//		studentDao.test();
//		
//		
//		
//	}
	
}	

