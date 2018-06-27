package com.iktpreobuka.final_project.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.controllers.util.Check;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.SubjectEntity;
import com.iktpreobuka.final_project.repository.SubjectRepository;

@Service
public class SubjectDaoImpl implements SubjectDao {
	
	@Autowired
	private SubjectRepository subjectRep;
	
	//Find all
	@Override
	public ResponseEntity<?> findAll(){
		try {
			return new ResponseEntity<>(subjectRep.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Find  by id
	@Override
	public ResponseEntity<?> getSubjectById(String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			SubjectEntity se=subjectRep.findById(id).get();
	
			return new ResponseEntity<>(se, HttpStatus.OK);
		
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Subject not found."), HttpStatus.NOT_FOUND);
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
			SubjectEntity se=subjectRep.findById(id).get();
	
			Boolean checkAnswer =Check.canDelete(se);
			if(checkAnswer==null)
				return new ResponseEntity<>(new RESTError(3,"canDelete mathod can`t answer properly."), HttpStatus.INTERNAL_SERVER_ERROR);
			else if(checkAnswer==false)
				return new ResponseEntity<>(new RESTError(3,"Subject can`t be deleted.(reference exists)"), HttpStatus.INTERNAL_SERVER_ERROR);	
			
			
			subjectRep.delete(se);
			return new ResponseEntity<>(se, HttpStatus.OK);
		
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Subject not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//POTREBNO ISPRAVITI----
	//Post new subject
	@Override
	public ResponseEntity<?> postNewSubject(SubjectEntity seBody) {
		try {
			SubjectEntity subject=new SubjectEntity();
	
			subject.setSubjectName(seBody.getSubjectName());
		
			subjectRep.save(subject);
			return new ResponseEntity<>(subject, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//POTREBNO ISPRAVITI
	//Put subject by id
	@Override
	public ResponseEntity<?> putSubjectById(SubjectEntity seBody, String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			SubjectEntity se=subjectRep.findById(id).get();
					
			se.setSubjectName(seBody.getSubjectName());	

			subjectRep.save(se);
			
			return new ResponseEntity<>(se, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Subject not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
