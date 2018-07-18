package com.iktpreobuka.final_project.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.controllers.dto.AvgMarkDTO;
import com.iktpreobuka.final_project.controllers.dto.MarkDTO;
import com.iktpreobuka.final_project.controllers.util.Check;
import com.iktpreobuka.final_project.controllers.util.DateValidation;
import com.iktpreobuka.final_project.controllers.util.EmailObject;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.MarkEntity;
import com.iktpreobuka.final_project.entities.MarkTypeEntity;
import com.iktpreobuka.final_project.entities.ParentEntity;
import com.iktpreobuka.final_project.entities.ProfessorEntity;
import com.iktpreobuka.final_project.entities.ScheduleEntity;
import com.iktpreobuka.final_project.entities.SchoolYearEntity;
import com.iktpreobuka.final_project.entities.StudentEntity;
import com.iktpreobuka.final_project.entities.UserEntity;
import com.iktpreobuka.final_project.repository.MarkRepository;
import com.iktpreobuka.final_project.repository.MarkTypeRepository;
import com.iktpreobuka.final_project.repository.ParentRepository;
import com.iktpreobuka.final_project.repository.ProfessorRepository;
import com.iktpreobuka.final_project.repository.ScheduleRepository;
import com.iktpreobuka.final_project.repository.SchoolYearRepository;
import com.iktpreobuka.final_project.repository.StudentRepository;
import com.iktpreobuka.final_project.repository.UserRepository;

@Service
public class MarkDaoImpl implements MarkDao {
	
	@Autowired
	private MarkRepository markRep;
	
	@Autowired
	private StudentRepository studentRep;
	
	@Autowired
	private ParentRepository parentRep;
	
	@Autowired
	private ProfessorRepository professorRep;

	@Autowired
	private ScheduleRepository scheduleRep;
	
	@Autowired
	private MarkTypeRepository markTypeRep;
	
	@Autowired
	private UserRepository userRep;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SchoolYearRepository schoolYearRep;
	
	@PersistenceContext
	EntityManager em;
	
	//Find all
	@Override
	public ResponseEntity<?> findAll(){
		try {
			return new ResponseEntity<>(markRep.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Find  by id
	@Override
	public ResponseEntity<?> getMarkById(String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			MarkEntity me=markRep.findById(id).get();
		
			return new ResponseEntity<>(me, HttpStatus.OK);

		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Mark not found."), HttpStatus.NOT_FOUND);
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
			MarkEntity me=markRep.findById(id).get();
				
			markRep.delete(me);
			return new ResponseEntity<>(me, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Mark not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//POTREBNO ISPRAVITI----UBACITI SLANJE MEJLA---SETOVATI STUDENTA,PREDMET I PROF
	//Post new mark
	@Override
	public ResponseEntity<?> postNewMark(MarkDTO meBody) {
		try {
			Authentication auth=SecurityContextHolder.getContext().getAuthentication(); 
			String email=auth.getName();
			UserEntity ue=userRep.findByEmail(email);
			
			MarkEntity mark=new MarkEntity();

			Byte markValue=Byte.parseByte(meBody.getMarkValueStr());
			Integer studentId=Integer.parseInt(meBody.getStudentStr());
			Integer scheduleId=Integer.parseInt(meBody.getScheduleStr());
			Integer markTypeId=Integer.parseInt(meBody.getMarkTypeStr());
			
			try {
				StudentEntity se=studentRep.findById(studentId).get();
				mark.setStudent(se);
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>(new RESTError(1,"Student with number "+studentId+" not found."), HttpStatus.NOT_FOUND);
			}
			
			try {
				ScheduleEntity se=scheduleRep.findById(scheduleId).get();
				if (se.getSchoolYear().getActive()==false) 
					return new ResponseEntity<>(new RESTError(2,"Year must be active"), HttpStatus.BAD_REQUEST);

				mark.setSchedule(se);
				if(ue.getRole().getName() == "ROLE_PROFESSOR")
					mark.setRatedBy(se.getTeacher());
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>(new RESTError(1,"Schedule with number "+scheduleId+" not found."), HttpStatus.NOT_FOUND);
			}	
				
			try {
				if(markTypeId < 4) {
					MarkTypeEntity mte=markTypeRep.findById(markTypeId).get();
					mark.setMarkType(mte);
				}else {
//					if(markValue == 0) {
//						MarkTypeEntity mte=markTypeRep.findById(5).get();
//						mark.setMarkType(mte);
//					}else 
					if(markValue == 1){
						MarkTypeEntity mte=markTypeRep.findById(6).get();
						mark.setMarkType(mte);
					}else {
						MarkTypeEntity mte=markTypeRep.findById(4).get();
						mark.setMarkType(mte);
					}
				}
			} catch (NoSuchElementException e) {
				return new ResponseEntity<>(new RESTError(1,"Mark type with number "+markTypeId+" not found."), HttpStatus.NOT_FOUND);
			}
			
			if(meBody.getDateRated()!=null) {
				String dateStr=meBody.getDateRated();
				String message=DateValidation.dateValidate(dateStr);
				Byte answer;
				
				if(message != null) 
					return new ResponseEntity<>(new RESTError(2,message), HttpStatus.BAD_REQUEST);
				DateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
				Date date=formatter.parse(dateStr);
				mark.setDateRated(date);
				
				answer=Check.getSemester(mark.getSchedule().getSchoolYear(), mark.getDateRated());
				
				if(answer!=Check.getSemester(mark.getSchedule().getSchoolYear(), new Date()))
					return new ResponseEntity<>(new RESTError(2,"Date must be within active semester"), HttpStatus.BAD_REQUEST);
				
				mark.setSemester(answer);
			}else {
				mark.setDateRated(new Date());
				mark.setSemester(Check.getSemester(mark.getSchedule().getSchoolYear(), new Date()));
			}
			
			if((mark.getMarkType().getId()==4 || mark.getMarkType().getId()==5 || mark.getMarkType().getId()==6 )&& ifFinalMarkExist(mark.getSchedule().getId(), mark.getStudent().getId(), mark.getSemester()))
				return new ResponseEntity<>(new RESTError(3,"Mark already exist"), HttpStatus.BAD_REQUEST);
			
			mark.setMarkValue(markValue);
			mark.setDescription(meBody.getDescriptionStr());			
			mark.setLocked(false);
			
			markRep.save(mark);
			
			for(ParentEntity pe :mark.getStudent().getParents()) {//send email to parents
				EmailObject eo=new EmailObject();
				String studentName=mark.getStudent().getName()+" "+mark.getStudent().getLastname();
				String subjectName=mark.getSchedule().getSubject().getSubject().getSubjectName();
				String profName=mark.getRatedBy().getName()+" "+mark.getRatedBy().getLastname();
				String text=emailService.textTemplateMark(studentName, mark.getMarkValue(),subjectName , mark.getDateRated(),profName);

				eo.setTo("fotos1992@gmail.com");//pe.getEmail()
				eo.setSubject("Mark notification");
				eo.setText(text);
				
				emailService.sendSimpleMessage(eo);
			}
						
			return new ResponseEntity<>(mark, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Mark not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}

	//Put mark by id
	@Override
	public ResponseEntity<?> putMarkById(MarkDTO meBody, String idString){
		try {
			Authentication auth=SecurityContextHolder.getContext().getAuthentication(); 
			String email=auth.getName();
			UserEntity ue=userRep.findByEmail(email);
			
			Integer id=Integer.parseInt(idString);			
			MarkEntity me=markRep.findById(id).get();
			
			if(meBody.getStudentStr()!=null){
				Integer studentId=Integer.parseInt(meBody.getStudentStr());
				if(studentId<1)
					return new ResponseEntity<>(new RESTError(2,"Student id number must be greater than 0!"), HttpStatus.BAD_REQUEST);	
				try {
					StudentEntity se=studentRep.findById(studentId).get();
					me.setStudent(se);
				} catch (NoSuchElementException e) {
					return new ResponseEntity<>(new RESTError(1,"Student with number "+studentId+" not found."), HttpStatus.NOT_FOUND);
				}
			}
			
			if(meBody.getScheduleStr()!=null){
				Integer sceduleId=Integer.parseInt(meBody.getScheduleStr());
				if(sceduleId<1)
					return new ResponseEntity<>(new RESTError(2,"Schedule id number must be greater than 0!"), HttpStatus.BAD_REQUEST);	
				try {
					ScheduleEntity se=scheduleRep.findById(sceduleId).get();
					me.setSchedule(se);
					if(ue.getRole().getName() == "ROLE_PROFESSOR")
						me.setRatedBy(se.getTeacher());
				} catch (NoSuchElementException e) {
					return new ResponseEntity<>(new RESTError(1,"Schedule with number "+sceduleId+" not found."), HttpStatus.NOT_FOUND);
				}
			}
			
			if(meBody.getMarkValueStr()!=null) {
				Byte markInt=Byte.parseByte(meBody.getMarkValueStr());
				if(markInt<1 || markInt>5)
					return new ResponseEntity<>(new RESTError(2,"Mark value must be between 1 and 5!"), HttpStatus.BAD_REQUEST);
				me.setMarkValue(markInt);
			}
			
			if(meBody.getMarkTypeStr()!=null){
				Integer typeId=Integer.parseInt(meBody.getMarkTypeStr());
				if(typeId<1)
					return new ResponseEntity<>(new RESTError(2,"Mark type id number must be greater than 0!"), HttpStatus.BAD_REQUEST);	
				try {
					if(typeId < 4) {
						MarkTypeEntity mte=markTypeRep.findById(typeId).get();
						me.setMarkType(mte);
					}else {
//						if(me.getMarkValue() == 0) {
//							MarkTypeEntity mte=markTypeRep.findById(5).get();
//							me.setMarkType(mte);
//						}else 
						if(me.getMarkValue() == 1){
							MarkTypeEntity mte=markTypeRep.findById(6).get();
							me.setMarkType(mte);
						}else {
							MarkTypeEntity mte=markTypeRep.findById(4).get();
							me.setMarkType(mte);
						}
					}				
				} catch (NoSuchElementException e) {
					return new ResponseEntity<>(new RESTError(1,"Mark type with number "+typeId+" not found."), HttpStatus.NOT_FOUND);
				}
				
				
			}
			
			

			if(meBody.getDescriptionStr()!=null) {
				if(meBody.getDescriptionStr().length()>49)
					return new ResponseEntity<>(new RESTError(2,"Description must be less than 50 characters long."), HttpStatus.BAD_REQUEST);
				me.setDescription(meBody.getDescriptionStr());
			}
				
			me.setLastUpdateDate(new Date());
			
			
			markRep.save(me);
			
			for(ParentEntity pe :me.getStudent().getParents()) {//send email to parents
				EmailObject eo=new EmailObject();
				String studentName=me.getStudent().getName()+" "+me.getStudent().getLastname();
				String subjectName=me.getSchedule().getSubject().getSubject().getSubjectName();
				String profName=me.getRatedBy().getName()+" "+me.getRatedBy().getLastname();
				String text=emailService.textTemplateMark(studentName, me.getMarkValue(),subjectName , me.getDateRated(),profName);

				eo.setTo("fotos1992@gmail.com"); // pe.getEmail()
				eo.setSubject("Mark notification");
				eo.setText(text);
				
				emailService.sendSimpleMessage(eo);
			}
			
			return new ResponseEntity<>(me, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Mark not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//get mark by student
	@Override
	public ResponseEntity<?> getMarkByStudent(String idString) {
		try {
			Integer id=Integer.parseInt(idString);
			StudentEntity se=studentRep.findById(id).get();
			List<MarkEntity> meList=markRep.findByStudent(se);
			
			return new ResponseEntity<>(meList, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Entity not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//get mark by parent
	@Override
	public ResponseEntity<?> getMarkByParent(String idString) {
		try {
			Integer id=Integer.parseInt(idString);
			ParentEntity pe=parentRep.findById(id).get();
			List<StudentEntity> seList=studentRep.findAllByParents(pe);
			List<MarkEntity> meList=markRep.findByStudentIn(seList);			
			
			return new ResponseEntity<>(meList, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Entity not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//get mark by prof
	@Override
	public ResponseEntity<?> getMarkByProf(String idString) {
		try {
			Integer id=Integer.parseInt(idString);
			ProfessorEntity pe=professorRep.findById(id).get();
			List<ScheduleEntity> seList=scheduleRep.findByTeacher(pe);
			List<MarkEntity> meList=markRep.findByScheduleIn(seList);
			
			return new ResponseEntity<>(meList, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Entity not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//generate final marks
	@Override
	public ResponseEntity<?> generateFinalMarks() {
		try {
			List<SchoolYearEntity> syeList=schoolYearRep.findByActive(true);
			Byte semester;
			
			if(syeList.size()==1) 
				semester=Check.getSemester(syeList.get(0), new Date());
			else 
				return new ResponseEntity<>(new RESTError(3,"Must be exactly one active year"),HttpStatus.INTERNAL_SERVER_ERROR);
			
			List<AvgMarkDTO> amList=this.selectMarksAvgFromDB(semester);
			List<MarkEntity> generatedMarksList=new ArrayList<>();
			
			MarkTypeEntity mtFinalMark=markTypeRep.findById(4).get();
			MarkTypeEntity mtUnrated=markTypeRep.findById(5).get();
			MarkTypeEntity mtCorrectionExam=markTypeRep.findById(6).get();
			
			for(AvgMarkDTO am: amList) {
				
				ScheduleEntity schedule=scheduleRep.findById(am.getScheduleID()).get();
				StudentEntity  student=studentRep.findById(am.getStudentID()).get();
					
				if(am.getAvg()!=null) {
					if(!ifFinalMarkExist(schedule.getId(), student.getId(), semester)) {	
						Long longBr=Math.round(am.getAvg());
						Byte br=longBr.byteValue();
						
						MarkEntity me=new MarkEntity();
										
						if(br==1)
							me.setMarkType(mtCorrectionExam);
						else
							me.setMarkType(mtFinalMark);
						
						me.setDateRated(new Date());
						me.setLocked(false);
						me.setMarkValue(br);
						me.setRatedBy(schedule.getTeacher());
						me.setSchedule(schedule);
						me.setStudent(student);
						me.setSemester(semester);
						me.setDescription("Generated mark");
						
						generatedMarksList.add(me);
					}
				}else {
					if(!ifFinalMarkExist(schedule.getId(), student.getId(), semester)) {
						MarkEntity me=new MarkEntity();
						Byte b=0;
						
						me.setMarkType(mtUnrated);
						me.setDateRated(new Date());
						me.setLocked(false);
						me.setMarkValue(b);
						me.setRatedBy(schedule.getTeacher());
						me.setSchedule(schedule);
						me.setStudent(student);
						me.setSemester(semester);
						me.setDescription("Generated mark");
						
						generatedMarksList.add(me);
					}
				}
			}
			markRep.saveAll(generatedMarksList);
			return new ResponseEntity<>(generatedMarksList, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Entity not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//lock all marks
	public ResponseEntity<?> lockMarks(){
		try {
			List<SchoolYearEntity> syeList=schoolYearRep.findByActive(true);
			Byte semester;
			
			if(syeList.size()==1) 
				semester=Check.getSemester(syeList.get(0), new Date());
			else 
				return new ResponseEntity<>(new RESTError(3,"Must be exactly one active year"),HttpStatus.INTERNAL_SERVER_ERROR);
	
			String sql=	"SELECT m "
					+ "FROM MarkEntity m "
					+ "LEFT JOIN ScheduleEntity sch ON m.schedule=sch.id "
					+ "LEFT JOIN SchoolYearEntity sye ON sye.id=sch.schoolYear "
					+ "WHERE sye.active=true AND m.semester=:semester AND m.markType!=5 AND m.markType!=6 ";
	
			Query query=em.createQuery(sql);
			query.setParameter("semester", semester);
			
			@SuppressWarnings("unchecked")
			List<MarkEntity> result=query.getResultList();
			
			for(MarkEntity me:result) 
				me.setLocked(true);
			
			markRep.saveAll(result);
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

// _______________________________________PRIVATE___________________________
	
	
	 private Boolean ifFinalMarkExist(Integer schedule, Integer student , Byte semester)throws Exception {
		String sql="SELECT m FROM ScheduleEntity sch RIGHT JOIN "
				+ " MarkEntity m ON m.schedule=sch.id "
				+ "LEFT JOIN StudentEntity s ON s.id=m.student "
				+ "WHERE m.semester=:semester AND sch.id=:schedule AND s.id=:student AND "
				+ " (m.markType=4 OR m.markType=5 OR m.markType=6)";
		
		Query query=em.createQuery(sql);
		query.setParameter("schedule", schedule);
		query.setParameter("student", student);
		query.setParameter("semester", semester);
		
		List<?> result=query.getResultList();
		
		if(result.size()>0) {
			return true;
		}else
			return false;
	}
	
	 
	 
	private List<AvgMarkDTO> selectMarksAvgFromDB(Byte semester)throws Exception{
		String sql=	"SELECT new com.iktpreobuka.final_project.controllers.dto.AvgMarkDTO( s.id, sch.id,avg(m.markValue)) "
				+ "FROM StudentEntity s LEFT JOIN ScheduleEntity sch ON sch.classEntity=s.classEntity "
				+ "LEFT JOIN MarkEntity m ON m.schedule=sch.id AND m.student=s.id "
				+ "AND (m.markType=1 OR m.markType=2 OR m.markType=3) AND m.semester=:semester "
				+ "GROUP BY s.id,sch.id";

		Query query=em.createQuery(sql);
		query.setParameter("semester", semester);
		
		@SuppressWarnings("unchecked")
		List<AvgMarkDTO> result=query.getResultList();
		
		return  result;
	}
	
	
	
	
	
	
	
	

	

	
}
