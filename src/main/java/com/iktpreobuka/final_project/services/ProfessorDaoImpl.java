package com.iktpreobuka.final_project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.controllers.dto.ChangePasswordDTO;
import com.iktpreobuka.final_project.controllers.dto.ProfessorDTO;
import com.iktpreobuka.final_project.controllers.util.Check;
import com.iktpreobuka.final_project.controllers.util.EmailObject;
import com.iktpreobuka.final_project.controllers.util.Encryption;
import com.iktpreobuka.final_project.controllers.util.RegExValidation;
import com.iktpreobuka.final_project.controllers.util.PasswordValidation;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.ProfessorEntity;
import com.iktpreobuka.final_project.entities.SubjectGradeEntity;
import com.iktpreobuka.final_project.repository.ProfessorRepository;
import com.iktpreobuka.final_project.repository.RoleRepository;
import com.iktpreobuka.final_project.repository.SubjectGradeRepository;

@Service
public class ProfessorDaoImpl implements ProfessorDao {

	@Autowired
	private ProfessorRepository professorRep;
	
	@Autowired
	private SubjectGradeRepository subjectGradeRep;
	
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
			return new ResponseEntity<>(professorRep.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Find  by id
	@Override
	public ResponseEntity<?> getProfessorById(String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			ProfessorEntity pe=professorRep.findById(id).get();
			
			return new ResponseEntity<>(pe, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Professor not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Find  professor by token
	@Override
	public ResponseEntity<?> getProfessorFromToken(){
			return userDao.getUserFromToken();	
	}
	
	//Delete by id
	@Override
	public ResponseEntity<?> deleteById(String idString){
		try {	
			Integer id=Integer.parseInt(idString);			
			ProfessorEntity pe=professorRep.findById(id).get();
			List<SubjectGradeEntity> sgeList=subjectGradeRep.findAllByProfessors(pe);
			
			
			Boolean checkAnswer =Check.canDelete(pe);
			if(checkAnswer==null)
				return new ResponseEntity<>(new RESTError(3,"canDelete mathod can`t answer properly."), HttpStatus.INTERNAL_SERVER_ERROR);
			else if(checkAnswer==false)
				return new ResponseEntity<>(new RESTError(3,"Professor can`t be deleted.(reference exists)"), HttpStatus.INTERNAL_SERVER_ERROR);
						
			professorRep.delete(pe);
			pe.setSubjects(sgeList);
			
			return new ResponseEntity<>(pe, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Professor not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Post new parent
	@Override
	public ResponseEntity<?> postNewProfessor(ProfessorDTO peBody){
		try {
			ProfessorEntity professor=new ProfessorEntity();
			String pass=passwordVaidation.generatePass();
			List<SubjectGradeEntity> sgeList =new ArrayList<SubjectGradeEntity>();	
					
			if(userDao.checkEmail(peBody.getEmail()))
				professor.setEmail(peBody.getEmail());
			else
				return new ResponseEntity<>(new RESTError(2,"Email must be unique."), HttpStatus.BAD_REQUEST);		

			
			
			if(peBody.getSubjectIdsStr() != null)
				for(String idStr : peBody.getSubjectIdsStr()) {
					Integer intId=Integer.parseInt(idStr);
					try {
						sgeList.add(subjectGradeRep.findById(intId).get());
					} catch (NoSuchElementException e) {
						return new ResponseEntity<>(new RESTError(1,"Subject with number "+intId+" not found."), HttpStatus.NOT_FOUND);
					}
				}
			
			professor.setSubjects(sgeList);
			professor.setName(peBody.getName());
			professor.setLastname(peBody.getLastname());
			professor.setUsername(peBody.getUsername());
			professor.setPassword(Encryption.getPassEncoded(pass));
			professor.setRole(roleRep.findById(2).get());
		
			professorRep.save(professor);
			
			EmailObject eo=new EmailObject();
			String text=emailService.textTemplatePass(pass);

			eo.setTo("fotos1992@gmail.com");//professor.getEmail()
			eo.setSubject("Password notification");
			eo.setText(text);
			
			emailService.sendSimpleMessage(eo);
			
			return new ResponseEntity<>(professor, HttpStatus.OK);
			
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//POTREBNO ISPRAVITI
	//Put professor by id
	@Override
	public ResponseEntity<?> putProfessorById(ProfessorDTO peBody, String idString){	
		try {
			Integer id=Integer.parseInt(idString);			
			ProfessorEntity pe=professorRep.findById(id).get();
			List<SubjectGradeEntity> sgeList =new ArrayList<SubjectGradeEntity>();	
			
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
					return new ResponseEntity<>(new RESTError(2,"Lastname must be between 3 and 15 characters long."), HttpStatus.BAD_REQUEST);
			
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
			
			
			if(peBody.getSubjectIdsStr() != null)
				for(String idStr : peBody.getSubjectIdsStr()) {
					Integer intId=Integer.parseInt(idStr);
					try {
						sgeList.add(subjectGradeRep.findById(intId).get());
					} catch (NoSuchElementException e) {
						return new ResponseEntity<>(new RESTError(1,"Subject with number "+intId+" not found."), HttpStatus.NOT_FOUND);
					}
				}
			
			pe.setSubjects(sgeList);
			
			professorRep.save(pe);
			return new ResponseEntity<>(pe, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Professor not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//change prof password by id
	@Override
	public ResponseEntity<?> changePassword(ChangePasswordDTO cpBody,String idString){
		return userDao.changePassword(cpBody,idString);
	}
	
	
	
}
