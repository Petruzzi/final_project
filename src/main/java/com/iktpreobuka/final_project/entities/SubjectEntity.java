package com.iktpreobuka.final_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SubjectEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView(Views.Admin.class)
	private Integer id;
	
	@NotNull(message="Ime predmeta mora biti popunjeno.")
	@Size(min=3, max=20, message = "Ime predmeta mora biti duzine izmedju {min} i {max} karaktera.")
	@JsonView(Views.Private.class)
	private String subjectName;
	
	

	
	@Version
	@JsonIgnore
	private Integer version;
	

	@JsonIgnore
	@OneToMany(mappedBy="subject",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private List<SubjectGradeEntity> subjectGrades=new ArrayList<SubjectGradeEntity>();
	
	
	
	

	
	
	
	public SubjectEntity() {
		super();
	}




	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public String getSubjectName() {
		return subjectName;
	}




	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}








	public Integer getVersion() {
		return version;
	}




	public void setVersion(Integer version) {
		this.version = version;
	}




	public List<SubjectGradeEntity> getSubjectGrades() {
		return subjectGrades;
	}




	public void setSubjectGrades(List<SubjectGradeEntity> subjectGrades) {
		this.subjectGrades = subjectGrades;
	}















}
