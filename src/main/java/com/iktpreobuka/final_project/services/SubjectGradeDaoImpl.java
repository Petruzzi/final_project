package com.iktpreobuka.final_project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.controllers.dto.SubjectGradeDTO;
import com.iktpreobuka.final_project.controllers.util.Check;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.ClassEntity;
import com.iktpreobuka.final_project.entities.GradeEntity;
import com.iktpreobuka.final_project.entities.ScheduleEntity;
import com.iktpreobuka.final_project.entities.StudentEntity;
import com.iktpreobuka.final_project.entities.SubjectEntity;
import com.iktpreobuka.final_project.entities.SubjectGradeEntity;
import com.iktpreobuka.final_project.repository.ClassRepository;
import com.iktpreobuka.final_project.repository.GradeRepository;
import com.iktpreobuka.final_project.repository.ScheduleRepository;
import com.iktpreobuka.final_project.repository.StudentRepository;
import com.iktpreobuka.final_project.repository.SubjectGradeRepository;
import com.iktpreobuka.final_project.repository.SubjectRepository;

@Service
public class SubjectGradeDaoImpl implements SubjectGradeDao {

	@Autowired
	private SubjectGradeRepository subjectGradeRep;
	
	@Autowired
	private SubjectRepository subjectRep;
	
	@Autowired
	private GradeRepository gradeRep;
	
	@Autowired
	private StudentRepository studentRep;
	
	@Autowired
	private ClassRepository classRep;
	
	@Autowired
	private ScheduleRepository scheduleRep;
	
	//Find all
	@Override
	public ResponseEntity<?> findAll() {
		try {
			return new ResponseEntity<>(subjectGradeRep.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//Find  by id
	@Override
	public ResponseEntity<?> getSubjectGradeById(String idString) {
		try {
			Integer id=Integer.parseInt(idString);			
			SubjectGradeEntity sge=subjectGradeRep.findById(id).get();
	
			return new ResponseEntity<>(sge, HttpStatus.OK);
		
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
			SubjectGradeEntity sge=subjectGradeRep.findById(id).get();
	
			Boolean checkAnswer =Check.canDelete(sge);
			if(checkAnswer==null)
				return new ResponseEntity<>(new RESTError(3,"canDelete mathod can`t answer properly."), HttpStatus.INTERNAL_SERVER_ERROR);
			else if(checkAnswer==false)
				return new ResponseEntity<>(new RESTError(3,"Subject can`t be deleted.(reference exists)"), HttpStatus.INTERNAL_SERVER_ERROR);	
			
			subjectGradeRep.delete(sge);
			return new ResponseEntity<>(sge, HttpStatus.OK);
		
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Subject not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//POTREBNO ISPRAVITI----
	//Post new subject grade
	@Override
	public ResponseEntity<?> postNewSubjectGrade(SubjectGradeDTO sgeBody) {
		try {
			SubjectGradeEntity subject=new SubjectGradeEntity();
			Integer classLoadInt=Integer.parseInt(sgeBody.getClassLoadStr());
			Integer subjectId=Integer.parseInt(sgeBody.getSubjectStr());
			Integer gradeId=Integer.parseInt(sgeBody.getGradeStr());
			SubjectEntity se=new SubjectEntity();
			GradeEntity ge=new GradeEntity();
									
			
			
			try {
				se=subjectRep.findById(subjectId).get();
				subject.setSubject(se);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>(new RESTError(1,"Subject with number "+subjectId+" not found."), HttpStatus.NOT_FOUND);
			}
			
			

			try {
				ge=gradeRep.findById(gradeId).get();
				subject.setGrade(ge);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>(new RESTError(1,"Grade with number "+gradeId+" not found."), HttpStatus.NOT_FOUND);
			}
			
			if(subjectGradeRep.findAllByGradeAndSubject(ge, se).size()!=0)
				return new ResponseEntity<>(new RESTError(1,"Subject "+se.getSubjectName()+" already exist in "+ge.getGradeValue()+". grade" ), HttpStatus.NOT_FOUND);
			
			
			subject.setSubjectStatus(sgeBody.getSubjectStatus());
			subject.setAffectAvg(sgeBody.getAffectAvg());
			subject.setClassLoad(classLoadInt);
						
			subjectGradeRep.save(subject);
			return new ResponseEntity<>(subject, HttpStatus.OK);
		
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//POTREBNO ISPRAVITI
	//Put subject grade by id
	@Override
	public ResponseEntity<?> putSubjectGradeById(SubjectGradeDTO sgeBody, String idString) {
		try {
			Integer id=Integer.parseInt(idString);
			SubjectGradeEntity subject=subjectGradeRep.findById(id).get();
			
			if(sgeBody.getSubjectStatus()!=null)
				subject.setSubjectStatus(sgeBody.getSubjectStatus());
				
			if(sgeBody.getAffectAvg()!=null)
				subject.setAffectAvg(sgeBody.getAffectAvg());
				
			if(sgeBody.getClassLoadStr()!=null) {
				Integer classLoadInt=Integer.parseInt(sgeBody.getClassLoadStr());
				if (classLoadInt>0) 
					subject.setClassLoad(classLoadInt);
				else
					return new ResponseEntity<>(new RESTError(2,"Class load must be greater than 0!"), HttpStatus.BAD_REQUEST);
			}
			
			if(sgeBody.getSubjectStr()!=null) {
				Integer subjectId=Integer.parseInt(sgeBody.getSubjectStr());	
				if(subjectId<1)
					return new ResponseEntity<>(new RESTError(2,"Subject id number must be greater than 0!"), HttpStatus.BAD_REQUEST);	
				try {
					SubjectEntity se=subjectRep.findById(subjectId).get();
					subject.setSubject(se);
				} catch (NoSuchElementException e) {
					return new ResponseEntity<>(new RESTError(1,"Subject with number "+subjectId+" not found."), HttpStatus.NOT_FOUND);
				}
			}
			
			if(sgeBody.getGradeStr()!=null) { 
				Integer gradeId=Integer.parseInt(sgeBody.getGradeStr());
				if(gradeId<1)
					return new ResponseEntity<>(new RESTError(2,"Grade id number must be greater than 0!"), HttpStatus.BAD_REQUEST);	
				try {
					GradeEntity ge=gradeRep.findById(gradeId).get();
					subject.setGrade(ge);
				} catch (NoSuchElementException e) {
					return new ResponseEntity<>(new RESTError(1,"Grade with number "+gradeId+" not found."), HttpStatus.NOT_FOUND);
				}
			}

			subjectGradeRep.save(subject);	
			return new ResponseEntity<>(subject, HttpStatus.OK);	
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Subject not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// PROVERITI  ... MOZDA DUPLIRA
	@Override
	public ResponseEntity<?> getSubjectsByStudentId(String idString) {
		try {
			Integer id=Integer.parseInt(idString);			
			StudentEntity se=studentRep.findById(id).get();
			List<StudentEntity> seList=new ArrayList<StudentEntity>();
			seList.add(se);
			
			ClassEntity ce=classRep.findByStudentsIn(seList);
			List<ScheduleEntity> scheList=scheduleRep.findByClassEntity(ce);
			
			List<SubjectGradeEntity> sgeList = new ArrayList<SubjectGradeEntity>();
			
			for(ScheduleEntity sche: scheList) {
				sgeList.add(sche.getSubject());
			}
			
			
			return new ResponseEntity<>(sgeList, HttpStatus.OK);
		
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Entity not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
