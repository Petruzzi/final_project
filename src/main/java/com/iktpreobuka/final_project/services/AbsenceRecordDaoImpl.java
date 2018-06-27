package com.iktpreobuka.final_project.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.AbsenceRecordEntity;
import com.iktpreobuka.final_project.repository.AbsenceRecordRepository;

@Service
public class AbsenceRecordDaoImpl implements AbsenceRecordDao {
	
	@Autowired
	private AbsenceRecordRepository absenceRecordRep;
	
	//Find all
	@Override
	public ResponseEntity<?> findAll(){
		try {
			return new ResponseEntity<>(absenceRecordRep.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Find  by id
	@Override
	public ResponseEntity<?> getAbsenceRecordById(String idString){
		try {		
			Integer id=Integer.parseInt(idString);			
			AbsenceRecordEntity are=absenceRecordRep.findById(id).get();
			
			return new ResponseEntity<>(are, HttpStatus.OK);
		
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Absence not found."), HttpStatus.NOT_FOUND);
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
			AbsenceRecordEntity are=absenceRecordRep.findById(id).get();
			
			absenceRecordRep.delete(are);
			return new ResponseEntity<>(are, HttpStatus.OK);
		
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Absence not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//POTREBNO ISPRAVITI----
	//Post new absence record
	@Override
	public ResponseEntity<?> postNewAbsenceRecord(AbsenceRecordEntity areBody) {
		try {	
			absenceRecordRep.save(areBody);
			return new ResponseEntity<>(areBody, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//POTREBNO ISPRAVITI
	//Put absence record by id
	@Override
	public ResponseEntity<?> putAbsenceRecordById(AbsenceRecordEntity areBody, String idString){
		try {
			Integer id=Integer.parseInt(idString);
			AbsenceRecordEntity are =absenceRecordRep.findById(id).get();
			
			
			
			
			return null;
			
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
