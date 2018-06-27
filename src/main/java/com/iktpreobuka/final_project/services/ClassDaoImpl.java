package com.iktpreobuka.final_project.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.controllers.dto.ClassDTO;
import com.iktpreobuka.final_project.controllers.util.Check;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.ClassEntity;
import com.iktpreobuka.final_project.entities.GradeEntity;
import com.iktpreobuka.final_project.entities.ProfessorEntity;
import com.iktpreobuka.final_project.entities.ScheduleEntity;
import com.iktpreobuka.final_project.repository.ClassRepository;
import com.iktpreobuka.final_project.repository.GradeRepository;
import com.iktpreobuka.final_project.repository.ProfessorRepository;
import com.iktpreobuka.final_project.repository.ScheduleRepository;

@Service
public class ClassDaoImpl implements ClassDao {
	
	@Autowired
	private ClassRepository classRep;
	
	@Autowired
	private ProfessorRepository professorRep;
	
	@Autowired
	private GradeRepository gradeRep;
	
	@Autowired
	private ScheduleRepository scheduleRep;
	
	//Find all
	@Override
	public ResponseEntity<?> findAll(){
		try {
			return new ResponseEntity<>(classRep.findAll(),HttpStatus.OK);	
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Find  by id
	@Override
	public ResponseEntity<?> getClassById(String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			ClassEntity ce=classRep.findById(id).get();
		
			return new ResponseEntity<>(ce, HttpStatus.OK);
		
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Class not found."), HttpStatus.NOT_FOUND);
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
			ClassEntity ce=classRep.findById(id).get();
		
			Boolean checkAnswer =Check.canDelete(ce);
			if(checkAnswer==null)
				return new ResponseEntity<>(new RESTError(3,"canDelete mathod can`t answer properly."), HttpStatus.INTERNAL_SERVER_ERROR);
			else if(checkAnswer==false)
				return new ResponseEntity<>(new RESTError(3,"Class can`t be deleted.(reference exists)"), HttpStatus.INTERNAL_SERVER_ERROR);	
			
			classRep.delete(ce);
			return new ResponseEntity<>(ce, HttpStatus.OK);
		
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Class not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//POTREBNO ISPRAVITI----
	//Post new class
	@Override
	public ResponseEntity<?> postNewClass(ClassDTO ceBody) {
		try {
			ClassEntity classE=new ClassEntity();
				
			
			//dodati proveru odeljenja da se ne bi desilo ponavljanje ODELJENJE RAZRED kombinacije
			Integer gradeInt=Integer.parseInt(ceBody.getGrade());		
			GradeEntity ge=gradeRep.findById(gradeInt).get();				
			classE.setGrade(ge);
			
		
			Integer classNumberInt=Integer.parseInt(ceBody.getClassNumberStr());		
			classE.setClassNumber(classNumberInt);
			

			Integer headTeacherId=Integer.parseInt(ceBody.getHeadTeacher());
			ProfessorEntity pe=professorRep.findById(headTeacherId).get();
			if(classRep.findByHeadTeacher(pe)==null)
				classE.setHeadTeacher(pe);
			else
				return new ResponseEntity<>(new RESTError(2,"Professor already has a class"), HttpStatus.BAD_REQUEST);
		
									
			classRep.save(classE);
			return new ResponseEntity<>(classE, HttpStatus.OK);
		
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Entity not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//POTREBNO ISPRAVITI
	//Put class by id
	@Override
	public ResponseEntity<?> putClassById(ClassDTO ceBody, String idString){
		try {
			Integer idInt=Integer.parseInt(idString);
			ClassEntity classE=classRep.findById(idInt).get();
			Integer classNumberInt=null;
			Integer gradeInt=null;
					
			if(ceBody.getGrade()!=null) {
				gradeInt=Integer.parseInt(ceBody.getGrade());		
				if (gradeInt>0 && gradeInt<9) {
					GradeEntity ge=gradeRep.findById(gradeInt).get();
					classE.setGrade(ge);
				} else 
					return new ResponseEntity<>(new RESTError(2,"Grade must be between 1 and 8!"), HttpStatus.BAD_REQUEST);
			}
			
			if(ceBody.getClassNumberStr()!=null) {
				classNumberInt=Integer.parseInt(ceBody.getClassNumberStr());	
				if (classNumberInt>0 && classNumberInt<5) 
					classE.setClassNumber(classNumberInt);
				else 
					return new ResponseEntity<>(new RESTError(2,"Class number must be between 1 and 4!"), HttpStatus.BAD_REQUEST);
			}
					
			if(ceBody.getHeadTeacher()!=null) {
				Integer headTeacherId=Integer.parseInt(ceBody.getHeadTeacher());
				if (headTeacherId>0) {
					ProfessorEntity pe=professorRep.findById(headTeacherId).get();
					if(classRep.findByHeadTeacher(pe)==null)
						classE.setHeadTeacher(pe);
					else
						return new ResponseEntity<>(new RESTError(2,"Professor already has a class"), HttpStatus.BAD_REQUEST);			
				}else
					return new ResponseEntity<>(new RESTError(2,"Head teacher number must be greater than 0!"), HttpStatus.BAD_REQUEST);
			}
		
			classRep.save(classE);
			return new ResponseEntity<>(classE, HttpStatus.OK);	

		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Entity not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Get class by prof
	@Override
	public ResponseEntity<?> getClassByProf(String profId){
		try {
			Integer idInt=Integer.parseInt(profId);
			ProfessorEntity pe=professorRep.findById(idInt).get();
			List<ScheduleEntity> seList=scheduleRep.findByTeacher(pe);
			List<ClassEntity> ceList=classRep.findBySchedulesIn(seList);
			
			
			return new ResponseEntity<>(ceList, HttpStatus.OK);	
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Entity not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	//Get class by head teacher
	@Override
	public ResponseEntity<?> getClassByHeadTeacher(String headTeacherId){
		try {
				Integer idInt=Integer.parseInt(headTeacherId);
				ProfessorEntity pe=professorRep.findById(idInt).get();
				ClassEntity ce=classRep.findByHeadTeacher(pe);
		
				return new ResponseEntity<>(ce, HttpStatus.OK);	

		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Entity not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	
	
}
