package com.iktpreobuka.final_project.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.controllers.dto.MarkDTO;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.FinalMarkEntity;
import com.iktpreobuka.final_project.entities.ScheduleEntity;
import com.iktpreobuka.final_project.entities.StudentEntity;
import com.iktpreobuka.final_project.repository.FinalMarkRepository;
import com.iktpreobuka.final_project.repository.ScheduleRepository;
import com.iktpreobuka.final_project.repository.StudentRepository;

@Service
public class FinalMarkDaoImpl implements FinalMarkDao {
	
	@Autowired
	private FinalMarkRepository finalMarkRep;
	
	@Autowired
	private StudentRepository studentRep;
	
	@Autowired
	private ScheduleRepository scheduleRep;
	
	
	//Find all
	@Override
	public ResponseEntity<?> findAll(){
		try {
			return new ResponseEntity<>(finalMarkRep.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Find  by id
	@Override
	public ResponseEntity<?> getFinalMarkById(String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			FinalMarkEntity fme=finalMarkRep.findById(id).get();
			
			return new ResponseEntity<>(fme, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Final mark not found."), HttpStatus.NOT_FOUND);
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
			FinalMarkEntity fme=finalMarkRep.findById(id).get();
		
			finalMarkRep.delete(fme);
			return new ResponseEntity<>(fme, HttpStatus.OK);
	
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Final mark not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//POTREBNO ISPRAVITI----UBACITI SLANJE MEJLA---SETOVATI STUDENTA,PREDMET I PROF
	//Post new final mark
	@Override
	public ResponseEntity<?> postNewFinalMark(MarkDTO fmeBody) {
		try {
			FinalMarkEntity mark=new FinalMarkEntity();

			Byte markValue=Byte.parseByte(fmeBody.getMarkValueStr());
			Integer studentId=Integer.parseInt(fmeBody.getStudentStr());
			Integer scheduleId=Integer.parseInt(fmeBody.getScheduleStr());
			
			try {
				StudentEntity se=studentRep.findById(studentId).get();
				mark.setStudent(se);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>(new RESTError(1,"Student with number "+studentId+" not found."), HttpStatus.NOT_FOUND);
			}
		
			try {
				ScheduleEntity se=scheduleRep.findById(scheduleId).get();
				mark.setSchedule(se);
				mark.setRatedBy(se.getTeacher());
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>(new RESTError(1,"Schedule with number "+scheduleId+" not found."), HttpStatus.NOT_FOUND);
			}	
				
			if(fmeBody.getDateRated()!=null) {
				DateFormat formatter;
				Date date;
				formatter=new SimpleDateFormat("dd-MM-yyyy");
				date=formatter.parse(fmeBody.getDateRated());
				mark.setDateRated(date);
				System.out.println(date);
			}else {
				mark.setDateRated(new Date());
			}
			
			mark.setLastUpdateDate(new Date());
			mark.setFinalMarkValue(markValue);
			mark.setDescription(fmeBody.getDescriptionStr());				
			
			finalMarkRep.save(mark);
			
			
			
			
			return new ResponseEntity<>(mark, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//POTREBNO ISPRAVITI
	//Put final mark by id
	@Override
	public ResponseEntity<?> putFinalMarkById(MarkDTO fmeBody, String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			FinalMarkEntity fme=finalMarkRep.findById(id).get();
			
			if(fmeBody.getStudentStr()!=null){
				Integer studentId=Integer.parseInt(fmeBody.getStudentStr());
				if(studentId<1)
					return new ResponseEntity<>(new RESTError(2,"Student id number must be greater than 0!"), HttpStatus.BAD_REQUEST);	
				try {
					StudentEntity se=studentRep.findById(studentId).get();
					fme.setStudent(se);
				} catch (NoSuchElementException e) {
					return new ResponseEntity<>(new RESTError(1,"Student with number "+studentId+" not found."), HttpStatus.NOT_FOUND);
				}
			}
			
			if(fmeBody.getScheduleStr()!=null){
				Integer scheduleId=Integer.parseInt(fmeBody.getScheduleStr());
				if(scheduleId<1)
					return new ResponseEntity<>(new RESTError(2,"Schedule id number must be greater than 0!"), HttpStatus.BAD_REQUEST);	
				try {
					ScheduleEntity se=scheduleRep.findById(scheduleId).get();
					fme.setSchedule(se);
					fme.setRatedBy(se.getTeacher());
				} catch (NoSuchElementException e) {
					return new ResponseEntity<>(new RESTError(1,"Schedule with number "+scheduleId+" not found."), HttpStatus.NOT_FOUND);
				}
			}
			
			if(fmeBody.getMarkValueStr()!=null) {
				Byte markInt=Byte.parseByte(fmeBody.getMarkValueStr());
				if(markInt<1 || markInt>5)
					return new ResponseEntity<>(new RESTError(2,"Mark value must be between 1 and 5!"), HttpStatus.BAD_REQUEST);
				fme.setFinalMarkValue(markInt);
			}
			
			if(fmeBody.getDescriptionStr()!=null) {
				if(fmeBody.getDescriptionStr().length()>49)
					return new ResponseEntity<>(new RESTError(2,"Description must be less than 50 characters long."), HttpStatus.BAD_REQUEST);
				fme.setDescription(fmeBody.getDescriptionStr());
			}
			
			fme.setLastUpdateDate(new Date());
			
			
			finalMarkRep.save(fme);
			return new ResponseEntity<>(fme, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Mark not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
}
