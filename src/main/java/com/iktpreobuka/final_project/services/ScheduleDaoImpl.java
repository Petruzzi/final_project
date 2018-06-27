package com.iktpreobuka.final_project.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.controllers.dto.ScheduleDTO;
import com.iktpreobuka.final_project.controllers.util.Check;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.ClassEntity;
import com.iktpreobuka.final_project.entities.ProfessorEntity;
import com.iktpreobuka.final_project.entities.ScheduleEntity;
import com.iktpreobuka.final_project.entities.SchoolYearEntity;
import com.iktpreobuka.final_project.entities.SubjectGradeEntity;
import com.iktpreobuka.final_project.repository.ClassRepository;
import com.iktpreobuka.final_project.repository.ProfessorRepository;
import com.iktpreobuka.final_project.repository.ScheduleRepository;
import com.iktpreobuka.final_project.repository.SchoolYearRepository;
import com.iktpreobuka.final_project.repository.SubjectGradeRepository;

@Service
public class ScheduleDaoImpl implements ScheduleDao {
	
	@Autowired
	private ScheduleRepository scheduleRep;

	@Autowired
	private ProfessorRepository professorRep;
	
	@Autowired
	private SubjectGradeRepository subjectGradeRep;
	
	@Autowired
	private ClassRepository classRep;
	
	@Autowired
	private SchoolYearRepository schoolYearRep;
	
	//Find all
	@Override
	public ResponseEntity<?> findAll(){
		try {
			return new ResponseEntity<>(scheduleRep.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Find  by id
	@Override
	public ResponseEntity<?> getScheduleById(String idString){	
		try {
			Integer id=Integer.parseInt(idString);			
			ScheduleEntity se=scheduleRep.findById(id).get();
				
			return new ResponseEntity<>(se, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Schedule not found."), HttpStatus.NOT_FOUND);
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
			ScheduleEntity se=scheduleRep.findById(id).get();	
		
			Boolean checkAnswer =Check.canDelete(se);
			if(checkAnswer==null)
				return new ResponseEntity<>(new RESTError(3,"canDelete mathod can`t answer properly."), HttpStatus.INTERNAL_SERVER_ERROR);
			else if(checkAnswer==false)
				return new ResponseEntity<>(new RESTError(3,"Schedule can`t be deleted.(reference exists)"), HttpStatus.INTERNAL_SERVER_ERROR);	
			
			scheduleRep.delete(se);
			return new ResponseEntity<>(se, HttpStatus.OK);
		
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Schedule not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//POTREBNO ISPRAVITI----
	//Post new schedule
	@Override
	public ResponseEntity<?> postNewSchedule(ScheduleDTO seBody) {
		try {
			ScheduleEntity schedule=new ScheduleEntity();
			Integer teacherInt=Integer.parseInt(seBody.getTeacherStr());
			Integer subjectInt=Integer.parseInt(seBody.getSubjectStr());
			Integer classInt=Integer.parseInt(seBody.getClassEntityStr());
			Integer yearInt=Integer.parseInt(seBody.getSchoolYearStr());
						
			try {
				ProfessorEntity pe=professorRep.findById(teacherInt).get();
				schedule.setTeacher(pe);;
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>(new RESTError(1,"Professor with number "+teacherInt+" not found."), HttpStatus.NOT_FOUND);
			}
			
			try {
				SubjectGradeEntity sge=subjectGradeRep.findById(subjectInt).get();
				schedule.setSubject(sge);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>(new RESTError(1,"Subject with number "+subjectInt+" not found."), HttpStatus.NOT_FOUND);
			}
			
			try {
				SchoolYearEntity sye=schoolYearRep.findById(yearInt).get();
				schedule.setSchoolYear(sye);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>(new RESTError(1,"School year with number "+yearInt+" not found."), HttpStatus.NOT_FOUND);
			}
			
			try {
				ClassEntity ce=classRep.findById(classInt).get();
				schedule.setClassEntity(ce);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>(new RESTError(1,"Class with number "+classInt+" not found."), HttpStatus.NOT_FOUND);
			}
			
			scheduleRep.save(schedule);
			return new ResponseEntity<>(schedule, HttpStatus.OK);
			
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//POTREBNO ISPRAVITI
	//Put schedule by id
	@Override
	public ResponseEntity<?> putScheduleById(ScheduleDTO seBody, String idString){	
		try {
			Integer scheduleId=Integer.parseInt(idString);
			ScheduleEntity schedule=scheduleRep.findById(scheduleId).get();

			if(seBody.getTeacherStr()!=null) {
				Integer teacherInt=Integer.parseInt(seBody.getTeacherStr());
				if(teacherInt<1)
					return new ResponseEntity<>(new RESTError(2,"Teacher id number must be greater than 0!"), HttpStatus.BAD_REQUEST);	
				try {
					ProfessorEntity pe=professorRep.findById(teacherInt).get();
					schedule.setTeacher(pe);
				} catch (NoSuchElementException e) {
					return new ResponseEntity<>(new RESTError(1,"Professor with number "+teacherInt+" not found."), HttpStatus.NOT_FOUND);
				}		
			}
			
			if(seBody.getSubjectStr()!=null) {
				Integer subjectInt=Integer.parseInt(seBody.getSubjectStr());
				if(subjectInt<1)
					return new ResponseEntity<>(new RESTError(2,"Subject id number must be greater than 0!"), HttpStatus.BAD_REQUEST);	
				try {
					SubjectGradeEntity sge=subjectGradeRep.findById(subjectInt).get();
					schedule.setSubject(sge);
				} catch (NoSuchElementException e) {
					return new ResponseEntity<>(new RESTError(1,"Subject with number "+subjectInt+" not found."), HttpStatus.NOT_FOUND);
				}		
			}
			
			if(seBody.getClassEntityStr()!=null) {
				Integer classInt=Integer.parseInt(seBody.getClassEntityStr());
				if(classInt<1)
					return new ResponseEntity<>(new RESTError(2,"Class id number must be greater than 0!"), HttpStatus.BAD_REQUEST);	
				try {
					ClassEntity ce=classRep.findById(classInt).get();
					schedule.setClassEntity(ce);
				} catch (NoSuchElementException e) {
					return new ResponseEntity<>(new RESTError(1,"Class with number "+classInt+" not found."), HttpStatus.NOT_FOUND);
				}		
			}
			
			if(seBody.getSchoolYearStr()!=null) {
				Integer yearInt=Integer.parseInt(seBody.getSchoolYearStr());
				if(yearInt<1)
					return new ResponseEntity<>(new RESTError(2,"School year id number must be greater than 0!"), HttpStatus.BAD_REQUEST);	
				try {
					SchoolYearEntity sye=schoolYearRep.findById(yearInt).get();
					schedule.setSchoolYear(sye);
				} catch (NoSuchElementException e) {
					return new ResponseEntity<>(new RESTError(1,"School year with number "+yearInt+" not found."), HttpStatus.NOT_FOUND);
				}		
			}
			
			scheduleRep.save(schedule);
			return new ResponseEntity<>(schedule, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Schedule not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
}
