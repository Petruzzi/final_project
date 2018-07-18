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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.enumeration.SubjectStatusEnum;
import com.iktpreobuka.final_project.security.Views;
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SubjectGradeEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView(Views.Private.class)
	private Integer id;

	@JsonView(Views.Private.class)
	private SubjectStatusEnum subjectStatus;//izborni ili ne
	
	@JsonView(Views.Private.class)
	private Boolean affectAvg;//da li utice na prosek
	
	@JsonView(Views.Private.class)
	private Integer classLoad;// fond casova
	
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="subject")
	@JsonView(Views.Private.class)
	private SubjectEntity subject;
	

	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="grade")
	@JsonView(Views.Private.class)
	private GradeEntity grade;
	
	@JsonIgnore
	@OneToMany(mappedBy="subject",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private List<ScheduleEntity> schedules=new ArrayList<ScheduleEntity>();

	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinTable(name="prof_can_teach",joinColumns=
			{@JoinColumn(name="subject_id",nullable=false,updatable=false)},
		inverseJoinColumns=
			{@JoinColumn(name="professor_id",nullable=false,updatable=false)})
	private List<ProfessorEntity> professors=new ArrayList<ProfessorEntity>();


	@Version
	@JsonIgnore
	private Integer version;



	public SubjectGradeEntity() {
		super();
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public SubjectStatusEnum getSubjectStatus() {
		return subjectStatus;
	}



	public void setSubjectStatus(SubjectStatusEnum subjectStatus) {
		this.subjectStatus = subjectStatus;
	}



	public Boolean getAffectAvg() {
		return affectAvg;
	}



	public void setAffectAvg(Boolean affectAvg) {
		this.affectAvg = affectAvg;
	}



	public Integer getClassLoad() {
		return classLoad;
	}



	public void setClassLoad(Integer classLoad) {
		this.classLoad = classLoad;
	}



	public SubjectEntity getSubject() {
		return subject;
	}



	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}



	public GradeEntity getGrade() {
		return grade;
	}



	public void setGrade(GradeEntity grade) {
		this.grade = grade;
	}



	public Integer getVersion() {
		return version;
	}



	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
	public List<ProfessorEntity> getProfessors() {
		return professors;
	}




	public void setProfessors(List<ProfessorEntity> professors) {
		this.professors = professors;
	}


	public List<ScheduleEntity> getSchedules() {
		return schedules;
	}




	public void setSchedules(List<ScheduleEntity> schedules) {
		this.schedules = schedules;
	}


}
