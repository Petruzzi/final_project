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
import com.iktpreobuka.final_project.controllers.util.EmailObject;
import com.iktpreobuka.final_project.controllers.util.Encryption;
import com.iktpreobuka.final_project.controllers.util.PasswordValidation;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.UserEntity;
import com.iktpreobuka.final_project.repository.UserRepository;

@Service
public class UserDaoImpl implements UserDao {

	@Autowired
	private UserRepository userRep;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordValidation passwordVaidation;
	
	
	@Override
	public ResponseEntity<?> changePassword(ChangePasswordDTO cpBody,String idString){
		try {	
			Integer id=Integer.parseInt(idString);			
			UserEntity ue=userRep.findById(id).get();		
			String message=passwordVaidation.passwordCheck(ue.getPassword(), cpBody.getOldPassword(), cpBody.getNewPassword(), cpBody.getRepeatNewPassword());
			if(message!=null)
				return new ResponseEntity<>(new RESTError(2,message), HttpStatus.BAD_REQUEST);
			
			ue.setPassword(Encryption.getPassEncoded(cpBody.getNewPassword()));
			
			userRep.save(ue);
			return new ResponseEntity<>(ue, HttpStatus.OK);
				
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"User not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@Override
	public ResponseEntity<?> changePasswordByToken(ChangePasswordDTO cpBody){
		try {	
			
			Authentication auth=SecurityContextHolder.getContext().getAuthentication(); 
			String email=auth.getName();
			UserEntity ue=userRep.findByEmail(email);
				
			String message=passwordVaidation.passwordCheck(ue.getPassword(), cpBody.getOldPassword(), cpBody.getNewPassword(), cpBody.getRepeatNewPassword());
			if(message!=null)
				return new ResponseEntity<>(new RESTError(2,message), HttpStatus.BAD_REQUEST);
			
			ue.setPassword(Encryption.getPassEncoded(cpBody.getNewPassword()));
			
			userRep.save(ue);
			return new ResponseEntity<>(ue, HttpStatus.OK);
				
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"User not found."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@Override
	public ResponseEntity<?> resetUserPassword(String idString){
		try {	
			Integer id=Integer.parseInt(idString);			
			UserEntity ue=userRep.findById(id).get();		
			String pass=passwordVaidation.generatePass();
			
			ue.setPassword(Encryption.getPassEncoded(pass));
			userRep.save(ue);
			
			EmailObject eo=new EmailObject();
			String text=emailService.textTemplatePass(pass);

			eo.setTo("fotos1992@gmail.com");//ue.getEmail()
			eo.setSubject("Password notification");
			eo.setText(text);
			
			emailService.sendSimpleMessage(eo);
		      
		    List<String> msg=new ArrayList<String>(); 
		    msg.add("Password successfully changed");
		      
			return new ResponseEntity<>(msg, HttpStatus.OK);
				
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"User not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Find user by token
	@Override
	public ResponseEntity<?> getUserFromToken(){
		try {
			Authentication auth=SecurityContextHolder.getContext().getAuthentication(); 
			String email=auth.getName();
			UserEntity ue=userRep.findByEmail(email);
			
			return new ResponseEntity<>(ue, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"User not found."), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Find user by id
	@Override
	public ResponseEntity<?> getUserById(String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			UserEntity ue=userRep.findById(id).get();
			return new ResponseEntity<>(ue, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"User not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	
	
	
	// metoda za proveru jedinstvenosti emaila
	public Boolean checkEmail(String str) {
		UserEntity ue = userRep.findByEmail(str);
		if(ue == null) {
			return true;
		}else
			return false;
	}

	

}	

	


