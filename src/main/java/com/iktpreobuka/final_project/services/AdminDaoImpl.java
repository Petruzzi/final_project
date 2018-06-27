package com.iktpreobuka.final_project.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.controllers.dto.ChangePasswordDTO;
import com.iktpreobuka.final_project.controllers.dto.UserDTO;
import com.iktpreobuka.final_project.controllers.util.EmailObject;
import com.iktpreobuka.final_project.controllers.util.EmailValidation;
import com.iktpreobuka.final_project.controllers.util.Encryption;
import com.iktpreobuka.final_project.controllers.util.PasswordValidation;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.AdminEntity;
import com.iktpreobuka.final_project.repository.AdminRepository;
import com.iktpreobuka.final_project.repository.RoleRepository;

@Service
public class AdminDaoImpl implements AdminDao{
	
	@Autowired
	private AdminRepository adminRep;
	
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
	public  ResponseEntity<?> findAll(){
		try {
			return new ResponseEntity<>(adminRep.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Find  by id
	@Override
	public ResponseEntity<?> getAdminById(String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			AdminEntity ae=adminRep.findById(id).get();
		
			return new ResponseEntity<>(ae, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Admin not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Delete by id 
	@Override
	public ResponseEntity<?> deleteById(String idString) {
		try {	
			Integer id=Integer.parseInt(idString);			
			AdminEntity ae=adminRep.findById(id).get();

			adminRep.delete(ae);
			return new ResponseEntity<>(ae, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Admin not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Post new admin
	@Override
	public ResponseEntity<?> postNewAdmin(UserDTO aeBody) {
		try {			
			AdminEntity admin=new AdminEntity();
			String pass=passwordVaidation.generatePass();
			
			admin.setName(aeBody.getName());
			admin.setLastname(aeBody.getLastname());
			admin.setUsername(aeBody.getUsername());
			admin.setPassword(Encryption.getPassEncoded(pass));
			admin.setEmail(aeBody.getEmail());			
			admin.setRole(roleRep.findById(1).get());
			
			adminRep.save(admin);
			
			
			EmailObject eo=new EmailObject();
			String text=emailService.textTemplatePass(pass);

			eo.setTo(admin.getEmail());
			eo.setSubject("Password notification");
			eo.setText(text);
			
			emailService.sendSimpleMessage(eo);
		
			
			return new ResponseEntity<>(admin, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//POTREBNO ISPRAVITI
	//Put admin by id
	@Override
	public ResponseEntity<?> putAdminById(UserDTO aeBody, String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			AdminEntity ae=adminRep.findById(id).get();
			
			if(aeBody.getName()!=null)
				if(aeBody.getName().length()>=3 && aeBody.getName().length()<=15)
					ae.setName(aeBody.getName());	
				else	
					return new ResponseEntity<>(new RESTError(2,"First name must be between 3 and 15 characters long."), HttpStatus.BAD_REQUEST);
			
			
			if(aeBody.getLastname()!=null)
				if(aeBody.getLastname().length()>=3 && aeBody.getLastname().length()<=15)
					ae.setLastname(aeBody.getLastname());	
				else	
					return new ResponseEntity<>(new RESTError(2,"Last name must be between 3 and 15 characters long."), HttpStatus.BAD_REQUEST);
			
			if(aeBody.getUsername()!=null)
				if(aeBody.getUsername().length()>=3 && aeBody.getUsername().length()<=15)
					ae.setUsername(aeBody.getUsername());	
				else	
					return new ResponseEntity<>(new RESTError(2,"Username must be between 3 and 15 characters long."), HttpStatus.BAD_REQUEST);
			
			if(aeBody.getEmail()!=null) {
				String email=aeBody.getEmail();
				if (EmailValidation.validate(email))
					ae.setEmail(email);
				else
					return new ResponseEntity<>(new RESTError(2,"Email must be exemple@gmail.com."), HttpStatus.BAD_REQUEST);
			}
				
			adminRep.save(ae);
			return new ResponseEntity<>(ae, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Admin not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//change admin password by id
	@Override
	public ResponseEntity<?> changePassword(ChangePasswordDTO cpBody,String idString){
		return userDao.changePassword(cpBody,idString);
	}
	
	//reset user password by id
	@Override
	public ResponseEntity<?> resetUserPassword(String idString){
		return userDao.resetUserPassword(idString);
	}
	
	
}
