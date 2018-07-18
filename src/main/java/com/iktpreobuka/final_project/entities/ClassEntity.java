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
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.security.Views;
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClassEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView(Views.Private.class)
	private Integer id;
	
	@JsonView(Views.Private.class)
	private Integer classNumber;// broj odeljenje
	
	@Version
	@JsonIgnore
	private Integer version;
	
	@JsonIgnore
	@OneToMany(mappedBy="classEntity",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private List<ScheduleEntity> schedules=new ArrayList<ScheduleEntity>();


	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="headTeacher")
	@JsonView(Views.Private.class)
	private ProfessorEntity headTeacher;
		


	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="grade")
	@JsonView(Views.Private.class)
	private GradeEntity grade;

	//@JsonIgnore
	@OneToMany(mappedBy="classEntity",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JsonView(Views.Private.class)
	private List<StudentEntity> students=new ArrayList<StudentEntity>();
	
	public ClassEntity(){
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



	public List<ScheduleEntity> getSchedules() {
		return schedules;
	}



	public void setSchedules(List<ScheduleEntity> schedules) {
		this.schedules = schedules;
	}



	public ProfessorEntity getHeadTeacher() {
		return headTeacher;
	}



	public void setHeadTeacher(ProfessorEntity headTeacher) {
		this.headTeacher = headTeacher;
	}



	public GradeEntity getGrade() {
		return grade;
	}



	public void setGrade(GradeEntity grade) {
		this.grade = grade;
	}



	public List<StudentEntity> getStudents() {
		return students;
	}



	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}



	public Integer getClassNumber() {
		return classNumber;
	}



	public void setClassNumber(Integer classNumber) {
		this.classNumber = classNumber;
	}
	
	
	
	
}
