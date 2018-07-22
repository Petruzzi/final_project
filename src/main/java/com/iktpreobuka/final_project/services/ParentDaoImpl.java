package com.iktpreobuka.final_project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.controllers.dto.ChangePasswordDTO;
import com.iktpreobuka.final_project.controllers.dto.ParentDTO;
import com.iktpreobuka.final_project.controllers.util.Check;
import com.iktpreobuka.final_project.controllers.util.EmailObject;
import com.iktpreobuka.final_project.controllers.util.Encryption;
import com.iktpreobuka.final_project.controllers.util.PasswordValidation;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.controllers.util.RegExValidation;
import com.iktpreobuka.final_project.entities.ParentEntity;
import com.iktpreobuka.final_project.entities.StudentEntity;
import com.iktpreobuka.final_project.repository.ParentRepository;
import com.iktpreobuka.final_project.repository.RoleRepository;
import com.iktpreobuka.final_project.repository.StudentRepository;

@Service
public class ParentDaoImpl implements ParentDao {
	
	@Autowired
	private ParentRepository parentRep;
	
	@Autowired
	private StudentRepository studentRep;
	
	@Autowired
	private RoleRepository roleRep;

	@Autowired
	private PasswordValidation passwordVaidation;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserDao userDao;
	
	//Find all
	@Override
	public ResponseEntity<?> findAll(){
		try {
			return new ResponseEntity<>(parentRep.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Find  by id
	@Override
	public ResponseEntity<?> getParentById(String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			ParentEntity pe=parentRep.findById(id).get();
		
			return new ResponseEntity<>(pe, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Parent not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Find  by token
	@Override
	public ResponseEntity<?> getParentFromToken(){
			return userDao.getUserFromToken();	
	}
	
	//Find  by token
	@Override
	public ResponseEntity<?> getStudentsFromParentToken(){
		try {
			Authentication auth=SecurityContextHolder.getContext().getAuthentication(); 
			String email=auth.getName();
			ParentEntity pe=parentRep.findByEmail(email);
			List<StudentEntity> seList=studentRep.findAllByParents(pe);	
			
			return new ResponseEntity<>(seList, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Parent not found."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Delete by id
	@Override
	public ResponseEntity<?> deleteById(String idString){
		try {					
			Integer id=Integer.parseInt(idString);			
			ParentEntity pe=parentRep.findById(id).get();
			List<StudentEntity> seList=studentRep.findAllByParents(pe);			
			
			Boolean checkAnswer =Check.canDelete(pe);
			if(checkAnswer==null)
				return new ResponseEntity<>(new RESTError(3,"canDelete mathod can`t answer properly."), HttpStatus.INTERNAL_SERVER_ERROR);
			else if(checkAnswer==false)
				return new ResponseEntity<>(new RESTError(3,"Parent can`t be deleted.(reference exists)"), HttpStatus.INTERNAL_SERVER_ERROR);

			parentRep.delete(pe);			
			pe.setStudents(seList);
			
			return new ResponseEntity<>(pe, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Parent not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	//Post new parent
	@Override
	public ResponseEntity<?> postNewParent(ParentDTO peBody){
		try {
			ParentEntity parent=new ParentEntity();
			String pass=passwordVaidation.generatePass();
			List<StudentEntity> seList =new ArrayList<StudentEntity>();	

			if(userDao.checkEmail(peBody.getEmail()))
				parent.setEmail(peBody.getEmail());	
			else
				return new ResponseEntity<>(new RESTError(2,"Email must be unique."), HttpStatus.BAD_REQUEST);		
			
			if(peBody.getStudentIdsStr() != null)
				for(String idStr : peBody.getStudentIdsStr()) {
					Integer intId=Integer.parseInt(idStr);
					try {
						seList.add(studentRep.findById(intId).get());
					} catch (NoSuchElementException e) {
						return new ResponseEntity<>(new RESTError(1,"Student with number "+intId+" not found."), HttpStatus.NOT_FOUND);
					}
				}
			
			parent.setStudents(seList);
			parent.setName(peBody.getName());
			parent.setLastname(peBody.getLastname());
			parent.setUsername(peBody.getUsername());
			parent.setPassword(Encryption.getPassEncoded(pass));
			parent.setRole(roleRep.findById(4).get());
					
			parentRep.save(parent);
			
			EmailObject eo=new EmailObject();
			String text=emailService.textTemplatePass(pass);

			eo.setTo("fotos1992@gmail.com");//parent.getEmail()
			eo.setSubject("Password notification");
			eo.setText(text);
			
			emailService.sendSimpleMessage(eo);
			
			return new ResponseEntity<>(parent, HttpStatus.OK);
			
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//POTREBNO ISPRAVITI
	//Put parent by id
	@Override
	public ResponseEntity<?> putParentById(ParentDTO peBody, String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			ParentEntity pe=parentRep.findById(id).get();
			List<StudentEntity> seList =new ArrayList<StudentEntity>();	
			
			if(peBody.getName()!=null)
				if(peBody.getName().length()>=3 && peBody.getName().length()<=15)
					if(RegExValidation.validateFirstLetter(peBody.getName()))
						pe.setName(peBody.getName());
					else
						return new ResponseEntity<>(new RESTError(2,"First name format must be first letter uppercase then lowercase(e.g. Name)"), HttpStatus.BAD_REQUEST);
				else	
					return new ResponseEntity<>(new RESTError(2,"First name must be between 3 and 15 characters long."), HttpStatus.BAD_REQUEST);
		
			if(peBody.getLastname()!=null)
				if(peBody.getLastname().length()>=3 && peBody.getLastname().length()<=15)
					if(RegExValidation.validateFirstLetter(peBody.getLastname()))
						pe.setLastname(peBody.getLastname());	
					else
						return new ResponseEntity<>(new RESTError(2,"Lastname format must be first letter uppercase then lowercase(e.g. Name)"), HttpStatus.BAD_REQUEST);
				else	
					return new ResponseEntity<>(new RESTError(2,"Last name must be between 3 and 15 characters long."), HttpStatus.BAD_REQUEST);
			
			if(peBody.getUsername()!=null)
				if(peBody.getUsername().length()>=3 && peBody.getUsername().length()<=15)
					pe.setUsername(peBody.getUsername());	
				else	
					return new ResponseEntity<>(new RESTError(2,"Username must be between 3 and 15 characters long."), HttpStatus.BAD_REQUEST);
			
			if(peBody.getEmail()!=null) {
				String email=peBody.getEmail();
				if(!email.equals(pe.getEmail())){
					if (RegExValidation.validateEmail(email))
						if(userDao.checkEmail(email))
							pe.setEmail(email);
						else
							return new ResponseEntity<>(new RESTError(2,"Email must be unique."), HttpStatus.BAD_REQUEST);
					else
						return new ResponseEntity<>(new RESTError(2,"Email must be exemple@gmail.com."), HttpStatus.BAD_REQUEST);
				}
			}
			


			if(peBody.getStudentIdsStr() != null)
				for(String idStr : peBody.getStudentIdsStr()) {
					Integer intId=Integer.parseInt(idStr);
					try {
						seList.add(studentRep.findById(intId).get());
					} catch (NoSuchElementException e) {
						return new ResponseEntity<>(new RESTError(1,"Student with number "+intId+" not found."), HttpStatus.NOT_FOUND);
					}
				}

			pe.setStudents(seList);
			
			parentRep.save(pe);
			return new ResponseEntity<>(pe, HttpStatus.OK);
	
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Parent not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//change parent password by id
	@Override
	public ResponseEntity<?> changePassword(ChangePasswordDTO cpBody,String idString){
			return userDao.changePassword(cpBody,idString);
	}	
		//		try {	
//			Integer id=Integer.parseInt(idString);			
//			ParentEntity ae=parentRep.findById(id).get();		
//			String message=passwordVaidation.passwordCheck(ae.getPassword(), cpBody.getOldPassword(), cpBody.getNewPassword(), cpBody.getRepeatNewPassword());
//			if(message!=null)
//				return new ResponseEntity<>(new RESTError(2,message), HttpStatus.BAD_REQUEST);
//			
//			ae.setPassword(Encryption.getPassEncoded(cpBody.getNewPassword()));
//			
//			parentRep.save(ae);
//			return new ResponseEntity<>(ae, HttpStatus.OK);
//				
//		} catch (NoSuchElementException e) {
//			return new ResponseEntity<>(new RESTError(1,"Parent not found."), HttpStatus.NOT_FOUND);
//		} catch (NumberFormatException e) {
//			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
//		}		
	
	
	
}
