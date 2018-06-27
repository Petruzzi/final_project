package com.iktpreobuka.final_project.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.controllers.util.Check;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.GradeEntity;
import com.iktpreobuka.final_project.repository.GradeRepository;

@Service
public class GradeDaoImpl implements GradeDao {
	
	@Autowired
	private GradeRepository gradeRep;
	
//	@Autowired
//	private SubjectRepository subjectRep;
	
	//Find all
	@Override
	public ResponseEntity<?> findAll(){
		try {
			return new ResponseEntity<>(gradeRep.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Find  by id
	@Override
	public ResponseEntity<?> getGradeById(String idString){
		try {			
			Integer id=Integer.parseInt(idString);			
			GradeEntity ge=gradeRep.findById(id).get();
			
			return new ResponseEntity<>(ge, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Grade not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}

	//Delete by id////treba prepraviti
	@Override
	public ResponseEntity<?> deleteById(String idString) {
		try {		
			Integer id=Integer.parseInt(idString);			
			GradeEntity ge=gradeRep.findById(id).get();
		//	List<SubjectEntity> seList=subjectRep.findAllByGrades(ge);
			
			Boolean checkAnswer =Check.canDelete(ge);
			if(checkAnswer==null)
				return new ResponseEntity<>(new RESTError(3,"canDelete mathod can`t answer properly."), HttpStatus.INTERNAL_SERVER_ERROR);
			else if(checkAnswer==false)
				return new ResponseEntity<>(new RESTError(3,"Grade can`t be deleted.(reference exists)"), HttpStatus.INTERNAL_SERVER_ERROR);	
			
			
			
			gradeRep.delete(ge);
			//ge.setSubjects(seList);
			
			return new ResponseEntity<>(ge, HttpStatus.OK);
		
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Grade not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//POTREBNO ISPRAVITI----
	//Post new grade
	@Override
	public ResponseEntity<?> postNewGrade(GradeEntity geBody) {
		try {
			GradeEntity grade=new GradeEntity();

			grade.setGradeName(geBody.getGradeName());
			grade.setGradeValue(geBody.getGradeValue());
		
			gradeRep.save(grade);
			return new ResponseEntity<>(grade, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//POTREBNO ISPRAVITI
	//Put grade by id
	@Override
	public ResponseEntity<?> putGradeById(GradeEntity geBody, String idString){
		try {
			Integer id=Integer.parseInt(idString);	
			GradeEntity grade=gradeRep.findById(id).get();
			
			if(geBody.getGradeName()!=null)
				if(geBody.getGradeName().length()>=3 && geBody.getGradeName().length()<=15)
					grade.setGradeName(geBody.getGradeName());
				else
					return new ResponseEntity<>(new RESTError(2,"Grade name must be between 3 and 15 characters long."), HttpStatus.BAD_REQUEST);
		
			if (geBody.getGradeValue()!=null)
				if(geBody.getGradeValue()>0 && geBody.getGradeValue()<9)
					grade.setGradeValue(geBody.getGradeValue());
				else
					return new ResponseEntity<>(new RESTError(2,"Grade must be between 1 and 8!"), HttpStatus.BAD_REQUEST);
			
			gradeRep.save(grade);
			return new ResponseEntity<>(grade, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Grade not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	
}
