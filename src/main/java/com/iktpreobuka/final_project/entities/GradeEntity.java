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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.security.Views;
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GradeEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView(Views.Admin.class)
	private Integer id;
	
	@NotNull(message="Grade must be provided.")
	@Min(value=1, message="Grade must be between 1 and 8!")
	@Max(value=8, message="Grade must be between 1 and 8!")
	@JsonView(Views.Private.class)	
	private Byte gradeValue;
	
	@NotNull(message="Grade name must be provided.")
	@Size(min=3, max=15, message = "Grade name must be between {min} and {max} characters long.")
	@JsonView(Views.Private.class)
	private String gradeName;
	
	@JsonIgnore
	@OneToMany(mappedBy="grade",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private List<SubjectGradeEntity> subjects=new ArrayList<SubjectGradeEntity>();
	
	
	@JsonIgnore
	@OneToMany(mappedBy="grade",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private List<ClassEntity> classEntity=new ArrayList<ClassEntity>();
	
	@Version
	@JsonIgnore
	private Integer version;
	
	
	public GradeEntity(){
		super();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Byte getGradeValue() {
		return gradeValue;
	}


	public void setGradeValue(Byte gradeValue) {
		this.gradeValue = gradeValue;
	}


	public String getGradeName() {
		return gradeName;
	}


	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}




	public List<SubjectGradeEntity> getSubjects() {
		return subjects;
	}


	public void setSubjects(List<SubjectGradeEntity> subjects) {
		this.subjects = subjects;
	}


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public List<ClassEntity> getClassEntity() {
		return classEntity;
	}


	public void setClassEntity(List<ClassEntity> classEntity) {
		this.classEntity = classEntity;
	}
	
	
	
	
}
