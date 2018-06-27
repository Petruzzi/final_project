package com.iktpreobuka.final_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProfessorEntity extends UserEntity{

	
	@JsonIgnore
	@OneToMany(mappedBy="teacher",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private List<ScheduleEntity> schedules=new ArrayList<ScheduleEntity>();
	
	@JsonIgnore
	@OneToOne(mappedBy="headTeacher",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private ClassEntity headTeacherOfClass;
	

	@ManyToMany(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinTable(name="prof_can_teach",joinColumns=
			{@JoinColumn(name="professor_id",nullable=false,updatable=false)},
		inverseJoinColumns=
			{@JoinColumn(name="subject_id",nullable=false,updatable=false)})
	@JsonView(Views.Professor.class)
	private List<SubjectGradeEntity> subjects=new ArrayList<SubjectGradeEntity>();
	

	
	@JsonIgnore
	@OneToMany(mappedBy="ratedBy",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private List<FinalMarkEntity> finalMarksRated=new ArrayList<FinalMarkEntity>();
	
	
	@JsonIgnore
	@OneToMany(mappedBy="ratedBy",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private List<MarkEntity> marksRated=new ArrayList<MarkEntity>();
	
	
	
	
	
	
	public ProfessorEntity(){
		super();
	}

	public List<ScheduleEntity> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<ScheduleEntity> schedules) {
		this.schedules = schedules;
	}

	public ClassEntity getHeadTeacherOfClass() {
		return headTeacherOfClass;
	}

	public void setHeadTeacherOfClass(ClassEntity headTeacherOfClass) {
		this.headTeacherOfClass = headTeacherOfClass;
	}

	public List<SubjectGradeEntity> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectGradeEntity> subjects) {
		this.subjects = subjects;
	}

	public List<FinalMarkEntity> getFinalMarksRated() {
		return finalMarksRated;
	}

	public void setFinalMarksRated(List<FinalMarkEntity> finalMarksRate) {
		this.finalMarksRated = finalMarksRate;
	}

	public List<MarkEntity> getMarksRated() {
		return marksRated;
	}

	public void setMarksRated(List<MarkEntity> marksRated) {
		this.marksRated = marksRated;
	}

	



	
	
}
