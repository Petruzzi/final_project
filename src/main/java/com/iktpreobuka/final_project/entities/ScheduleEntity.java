package com.iktpreobuka.final_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ScheduleEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView(Views.Admin.class)
	private Integer id;
	
	@Version
	@JsonIgnore
	private Integer version;
	

	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="teacher")
	@JsonView(Views.Private.class)
	private ProfessorEntity teacher;
	

	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="subject")
	@JsonView(Views.Private.class)
	private SubjectGradeEntity subject;
	
	

	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="classEntity")
	@JsonView(Views.Private.class)
	private ClassEntity classEntity;
	
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="schoolYear")
	@JsonView(Views.Private.class)
	private SchoolYearEntity schoolYear;
	
	@JsonIgnore
	@OneToMany(mappedBy="schedule",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private List<MarkEntity> marks;
	
	@JsonIgnore
	@OneToMany(mappedBy="schedule",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private List<FinalMarkEntity> finalMarks;
	
	@JsonIgnore
	@OneToMany(mappedBy="schedule",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private List<AbsenceRecordEntity> absenceRecord=new ArrayList<AbsenceRecordEntity>();
	
	
	public ScheduleEntity(){
		super();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public ProfessorEntity getTeacher() {
		return teacher;
	}


	public void setTeacher(ProfessorEntity teacher) {
		this.teacher = teacher;
	}


	public SubjectGradeEntity getSubject() {
		return subject;
	}


	public void setSubject(SubjectGradeEntity subject) {
		this.subject = subject;
	}


	public ClassEntity getClassEntity() {
		return classEntity;
	}


	public void setClassEntity(ClassEntity classEntity) {
		this.classEntity = classEntity;
	}


	public SchoolYearEntity getSchoolYear() {
		return schoolYear;
	}


	public void setSchoolYear(SchoolYearEntity schoolYear) {
		this.schoolYear = schoolYear;
	}


	public List<MarkEntity> getMarks() {
		return marks;
	}


	public void setMarks(List<MarkEntity> marks) {
		this.marks = marks;
	}


	public List<FinalMarkEntity> getFinalMarks() {
		return finalMarks;
	}


	public void setFinalMarks(List<FinalMarkEntity> finalMarks) {
		this.finalMarks = finalMarks;
	}


	public List<AbsenceRecordEntity> getAbsenceRecord() {
		return absenceRecord;
	}


	public void setAbsenceRecord(List<AbsenceRecordEntity> absenceRecord) {
		this.absenceRecord = absenceRecord;
	}
	
	
	

}
