package com.iktpreobuka.final_project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.controllers.dto.ChangePasswordDTO;
import com.iktpreobuka.final_project.controllers.dto.StudentDTO;
import com.iktpreobuka.final_project.controllers.util.Check;
import com.iktpreobuka.final_project.controllers.util.EmailObject;
import com.iktpreobuka.final_project.controllers.util.Encryption;
import com.iktpreobuka.final_project.controllers.util.PasswordValidation;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.controllers.util.RegExValidation;
import com.iktpreobuka.final_project.entities.ClassEntity;
import com.iktpreobuka.final_project.entities.ParentEntity;
import com.iktpreobuka.final_project.entities.ScheduleEntity;
import com.iktpreobuka.final_project.entities.StudentEntity;
import com.iktpreobuka.final_project.repository.ClassRepository;
import com.iktpreobuka.final_project.repository.ParentRepository;
import com.iktpreobuka.final_project.repository.RoleRepository;
import com.iktpreobuka.final_project.repository.ScheduleRepository;
import com.iktpreobuka.final_project.repository.StudentRepository;

@Service
public class StudentDaoImpl implements StudentDao {
	
	@Autowired
	private StudentRepository studentRep;
	
	@Autowired
	private ParentRepository parentRep;

	@Autowired
	private ClassRepository classRep;
	
	@Autowired
	private RoleRepository roleRep;
	
	@Autowired
	private ScheduleRepository scheduleRep;
	
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
			return new ResponseEntity<>(studentRep.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Find  by id
	@Override
	public ResponseEntity<?> getStudentById(String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			StudentEntity se=studentRep.findById(id).get();

			return new ResponseEntity<>(se, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Student not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Find  student by token
	@Override
	public ResponseEntity<?> getStudentFromToken(){
			return userDao.getUserFromToken();	
	}
	
	//Delete by id
	@Override
	public ResponseEntity<?> deleteById(String idString) {
		try {
			Integer id=Integer.parseInt(idString);			
			StudentEntity se=studentRep.findById(id).get();
						
			Boolean checkAnswer =Check.canDelete(se);
			if(checkAnswer==null)
				return new ResponseEntity<>(new RESTError(3,"canDelete mathod can`t answer properly."), HttpStatus.INTERNAL_SERVER_ERROR);
			else if(checkAnswer==false)
				return new ResponseEntity<>(new RESTError(3,"Student can`t be deleted.(reference exists)"), HttpStatus.INTERNAL_SERVER_ERROR);	
			
			studentRep.delete(se);
			return new ResponseEntity<>(se, HttpStatus.OK);
		
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Student not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//Post new student
	@Override
	public ResponseEntity<?> postNewStudent(StudentDTO seBody) {
		try {
			StudentEntity student=new StudentEntity();
			String pass=passwordVaidation.generatePass();
			List<ParentEntity> peList =new ArrayList<ParentEntity>();	
			Integer classId;
			
			
			if(userDao.checkEmail(seBody.getEmail()))
				student.setEmail(seBody.getEmail());
			else
				return new ResponseEntity<>(new RESTError(2,"Email must be unique."), HttpStatus.BAD_REQUEST);

			
			
			if(seBody.getClassIdStr()!=null) {//set class if not null
				classId=Integer.parseInt(seBody.getClassIdStr());
				ClassEntity classE=classRep.findById(classId).get();
				
				// ubaceno zbog bug-a Error: Found shared references to a collection:
				List<ScheduleEntity> schList = new ArrayList<ScheduleEntity>();
				for(ScheduleEntity se1 : classE.getSchedules()) 
					schList.add(se1);
				//_______________________________________________________________
				
				student.setClassEntity(classE);
				student.setSchedules(schList);
			}
			
			for(String idStr : seBody.getParentIdsStr()) {//set parents
				Integer intId=Integer.parseInt(idStr);
				try {
					peList.add(parentRep.findById(intId).get());
				} catch (NoSuchElementException e) {
					return new ResponseEntity<>(new RESTError(1,"Parent with number "+intId+" not found."), HttpStatus.NOT_FOUND);
				}
			}
			
			student.setParents(peList);
			student.setName(seBody.getName());
			student.setLastname(seBody.getLastname());
			student.setUsername(seBody.getUsername());
			student.setPassword(Encryption.getPassEncoded(pass));
			student.setRole(roleRep.findById(3).get());
		
			studentRep.save(student);
			
			EmailObject eo=new EmailObject();
			String text=emailService.textTemplatePass(pass);

			eo.setTo("fotos1992@gmail.com");//student.getEmail()
			eo.setSubject("Password notification");
			eo.setText(text);
			
			emailService.sendSimpleMessage(eo);
			
			return new ResponseEntity<>(student, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Class not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//POTREBNO ISPRAVITI
	//Put student by id
	@Override
	public ResponseEntity<?> putStudentById(StudentDTO seBody, String idString){
		try {
			Integer id=Integer.parseInt(idString);			
			StudentEntity se=studentRep.findById(id).get();
			List<ParentEntity> peList =new ArrayList<ParentEntity>();	
			Integer classId;
			
			if(seBody.getClassIdStr()!=null) {//set class if not null
				classId=Integer.parseInt(seBody.getClassIdStr());
				ClassEntity classE=classRep.findById(classId).get();
				se.setClassEntity(classE);
				
				// presipanje schedules iz class u student
				
				List<ScheduleEntity> classSchList = classE.getSchedules();
				List<ScheduleEntity> oldStudentSchList = se.getSchedules();
				List<ScheduleEntity> newStudentSchList = new ArrayList<ScheduleEntity>();
				
				for(ScheduleEntity se1 : classSchList) // dodaje sve u staru listu.......OSTAJU DUPLIKATI
					oldStudentSchList.add(se1);
				
				newStudentSchList = oldStudentSchList.stream() // brisanje duplikata
													.distinct()
													.collect(Collectors.toList());

				se.setSchedules(newStudentSchList);

			}	
			if(seBody.getName()!=null)
				if(seBody.getName().length()>=3 && seBody.getName().length()<=15)
					if(RegExValidation.validateFirstLetter(seBody.getName()))
						se.setName(seBody.getName());		
					else
						return new ResponseEntity<>(new RESTError(2,"First name format must be first letter uppercase then lowercase(e.g. Name)"), HttpStatus.BAD_REQUEST);
				else	
					return new ResponseEntity<>(new RESTError(2,"First name must be between 3 and 15 characters long."), HttpStatus.BAD_REQUEST);
		
			if(seBody.getLastname()!=null)
				if(seBody.getLastname().length()>=3 && seBody.getLastname().length()<=15)
					if(RegExValidation.validateFirstLetter(seBody.getLastname()))
						se.setLastname(seBody.getLastname());	
					else
						return new ResponseEntity<>(new RESTError(2,"Lastname format must be first letter uppercase then lowercase(e.g. Name)"), HttpStatus.BAD_REQUEST);
				else	
					return new ResponseEntity<>(new RESTError(2,"Last name must be between 3 and 15 characters long."), HttpStatus.BAD_REQUEST);
			
			if(seBody.getUsername()!=null)
				if(seBody.getUsername().length()>=3 && seBody.getUsername().length()<=15)
					se.setUsername(seBody.getUsername());	
				else	
					return new ResponseEntity<>(new RESTError(2,"Username must be between 3 and 15 characters long."), HttpStatus.BAD_REQUEST);
			
			if(seBody.getEmail()!=null) {
				String email=seBody.getEmail();
				if(!email.equals(se.getEmail())){
					if (RegExValidation.validateEmail(email)) 
						if(userDao.checkEmail(email))
							se.setEmail(email);
						else
							return new ResponseEntity<>(new RESTError(2,"Email must be unique."), HttpStatus.BAD_REQUEST);
					else
						return new ResponseEntity<>(new RESTError(2,"Email must be exemple@gmail.com."), HttpStatus.BAD_REQUEST);
				}
			}
			
			if(seBody.getParentIdsStr().size()>0) {
				for(String idStr : seBody.getParentIdsStr()) {
					Integer intId=Integer.parseInt(idStr);
					try {
						peList.add(parentRep.findById(intId).get());
					} catch (NoSuchElementException e) {
						return new ResponseEntity<>(new RESTError(1,"Parent with number "+intId+" not found."), HttpStatus.NOT_FOUND);
					}
				}
				se.setParents(peList);
			}
					
			studentRep.save(se);	
			return new ResponseEntity<>(se, HttpStatus.OK);	
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(new RESTError(1,"Student not found."), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new RESTError(2,"Please fill up the whole number."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(3,"Error: "+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//change student password by id
	@Override
	public ResponseEntity<?> changePassword(ChangePasswordDTO cpBody,String idString){
		return userDao.changePassword(cpBody,idString);
	}
	
	
	
	
	
	
//	// TEST
//	public void test() {
//		
//		userDao.test();
//	
//		
//		
//	}

	
	
	
	
}
