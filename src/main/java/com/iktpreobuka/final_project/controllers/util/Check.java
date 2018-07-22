package com.iktpreobuka.final_project.controllers.util;

import java.util.Date;

import com.iktpreobuka.final_project.entities.ClassEntity;
import com.iktpreobuka.final_project.entities.GradeEntity;
import com.iktpreobuka.final_project.entities.ParentEntity;
import com.iktpreobuka.final_project.entities.ProfessorEntity;
import com.iktpreobuka.final_project.entities.ScheduleEntity;
import com.iktpreobuka.final_project.entities.SchoolYearEntity;
import com.iktpreobuka.final_project.entities.StudentEntity;
import com.iktpreobuka.final_project.entities.SubjectEntity;
import com.iktpreobuka.final_project.entities.SubjectGradeEntity;

public class Check {

	public static Boolean canDelete(Object x) {
		
		if(x.getClass().equals(StudentEntity.class)) {
			System.out.println("pronasao studenta");	
			StudentEntity se=(StudentEntity)x;
			if( se.getMarks().size()<1)//&& se.getClassEntity()==null
				return true;
			else
				return false;			
		}
		
		if(x.getClass().equals(ParentEntity.class)) {
			System.out.println("pronasao roditelja");
			ParentEntity pe=(ParentEntity)x;			
			if(pe.getStudents().size()<1)
				return true;
			else
				return false;
		} 
		
		if(x.getClass().equals(ProfessorEntity.class)) {
			System.out.println("pronasao prof");
			ProfessorEntity pe=(ProfessorEntity)x;	
			if(pe.getSchedules().size()<1 && pe.getHeadTeacherOfClass()==null && pe.getMarksRated().size()<1)
				return true;
			else
				return false;
		}
		
		if(x.getClass().equals(SubjectEntity.class)) {
			System.out.println("pronasao subject");
			SubjectEntity se=(SubjectEntity)x;	
			if(se.getSubjectGrades().size()<1)
				return true;
			else
				return false;
		}
		
		if(x.getClass().equals(SubjectGradeEntity.class)) {
			System.out.println("pronasao sub grade");
			SubjectGradeEntity sge=(SubjectGradeEntity)x;
			if(sge.getSchedules().size()<1)
				return true;
			else
				return false;
		}
		
		if(x.getClass().equals(ScheduleEntity.class)) {
			System.out.println("pronasao schedule");
			ScheduleEntity se=(ScheduleEntity)x;	
			if( se.getMarks().size()<1 && se.getAbsenceRecord().size()<1)
				return true;
			else
				return false;
		}
		
		if(x.getClass().equals(ClassEntity.class)) {
			System.out.println("pronasao class");
			ClassEntity ce=(ClassEntity)x;	
			if(ce.getSchedules().size()<1 && ce.getStudents().size()<1)
				return true;
			else
				return false;
		}		
		
		if(x.getClass().equals(GradeEntity.class)) {
			System.out.println("pronasao grade");
			GradeEntity ge=(GradeEntity)x;	
			if(ge.getClassEntity().size()<1 && ge.getSubjects().size()<1)
				return true;
			else
				return false;
		}
	
		return null;
	
	}
	
	public static Byte getSemester(SchoolYearEntity sye,Date date) {
		
		if(date.after(sye.getStartDate())  && date.before(sye.getSemesterDate()) ){
			
		//	System.out.println("prvi");
			return 1;
			
		}else if (date.after(sye.getSemesterDate()) && date.before(sye.getEndDate())){
		//	System.out.println("drugi");
			return 2;
		}
		
		//System.out.println("nije nadjen");
		return 0;
	}
	
	

	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Date d1=new Date(1519862400000l); 
		//Date d2=new Date(1533081600000l); 
		
		//System.out.println("d:"+d2.toString());
		//System.out.println("d2:"+d2.toString());
		
		SchoolYearEntity y1=new SchoolYearEntity();
		//y1.setStartDate(new Date(1514764800000l));
		System.out.println("s:"+y1.getStartDate().toString());
		//y1.setSemesterDate(new Date(1527811200000l));
		System.out.println("middle:"+y1.getSemesterDate().toString());
		//y1.setEndDate(new Date(1543622400000l));
		System.out.println("e:"+y1.getEndDate().toString());
		
		
		Check.getSemester(y1, d1);
		
	}
	
}
