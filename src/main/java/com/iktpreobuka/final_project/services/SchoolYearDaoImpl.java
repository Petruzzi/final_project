package com.iktpreobuka.final_project.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.controllers.dto.SchoolYearDTO;
import com.iktpreobuka.final_project.controllers.util.DateValidation;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.SchoolYearEntity;
import com.iktpreobuka.final_project.repository.SchoolYearRepository;

@Service
public class SchoolYearDaoImpl implements SchoolYearDao {

	@Autowired
	private SchoolYearRepository schoolYearRep;
	
	//Find all
	@Override
	public ResponseEntity<?> findAll(){
		try {
			return new ResponseEntity<>(schoolYearRep.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Find  by id
	@Override
	public ResponseEntity<?> getSchoolYearById(String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			SchoolYearEntity sye=schoolYearRep.findById(id).get();
		
			return new ResponseEntity<>(sye, HttpStatus.OK);
		
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Professor not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//Delete by id
	@Override
	public ResponseEntity<?> deleteById(String idString){	
		try {
			Integer id=Integer.parseInt(idString);			
			SchoolYearEntity sye=schoolYearRep.findById(id).get();
		
			schoolYearRep.delete(sye);
			return new ResponseEntity<>(sye, HttpStatus.OK);
		
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Professor not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//POTREBNO ISPRAVITI
	//Post new school year
	@Override
	public ResponseEntity<?> postNewSchoolYear(SchoolYearDTO syeBody){
		try {
			SchoolYearEntity schoolYear=new SchoolYearEntity();
			String startDateStr=syeBody.getStartDateStr();
			String semesterDateStr=syeBody.getSemesterDateStr();
			String endDateStr=syeBody.getEndDateStr();	
					
			List<String> messages=new ArrayList<>();
			
			messages.add(DateValidation.dateValidate(startDateStr));
			messages.add(DateValidation.dateValidate(semesterDateStr));
			messages.add(DateValidation.dateValidate(endDateStr));
			for(String msg:messages) 
				if(msg!=null) 
					return new ResponseEntity<>(new RESTError(2,msg), HttpStatus.BAD_REQUEST);

			DateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
			Date startDate=formatter.parse(startDateStr);
			Date semesterDate=formatter.parse(semesterDateStr);
			Date endDate=formatter.parse(endDateStr);
			
			schoolYear.setStartDate(startDate);
			schoolYear.setSemesterDate(semesterDate);
			schoolYear.setEndDate(endDate);
			
			//provera da li je start<semester<end i da li je start+1=end
			String answer=DateValidation.checkStartSemesterEndDates(startDate, semesterDate, endDate);
			if(answer!=null)
				return new ResponseEntity<>(new RESTError(2,answer), HttpStatus.BAD_REQUEST);
	
			//dobiti string "yy/yy" iz datuma pocetka i kraja skolske god
			@SuppressWarnings("deprecation")
			Integer startInt = startDate.getYear()-100;
			String startStr = startInt.toString();
			if(startStr.length() < 2) startStr="0"+startStr;
			
			@SuppressWarnings("deprecation")
			Integer endInt = endDate.getYear()-100;
			String endStr = endInt.toString();
			if(endStr.length() < 2) endStr = "0"+endStr;
			
			String yearName = startStr + "/" + endStr;

			schoolYear.setSchoolYearName(yearName);
			schoolYear.setActive(false);
			
			//schoolYearRep.save(schoolYear);
			return new ResponseEntity<>(schoolYear, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//POTREBNO ISPRAVITI
	//Put professor by id
	@Override
	public ResponseEntity<?> putSchoolYearById(SchoolYearDTO syeBody, String idString){
		
		try {
			return null;
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
}
