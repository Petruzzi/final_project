package com.iktpreobuka.final_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StudentEntity extends UserEntity{

	@JsonIgnore
	@OneToMany(mappedBy="student",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private List<MarkEntity> marks=new ArrayList<MarkEntity>();
	
	@JsonIgnore
	@OneToMany(mappedBy="student",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private List<FinalMarkEntity> finalMarks=new ArrayList<FinalMarkEntity>();
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinTable(name="parent_student",joinColumns=
			{@JoinColumn(name="student_id",nullable=false,updatable=false)},
		inverseJoinColumns=
			{@JoinColumn(name="parent_id",nullable=false,updatable=false)})
	List<ParentEntity> parents=new ArrayList<ParentEntity>();
	
	
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="classEntity")
	@JsonView(Views.Private.class)
	private ClassEntity classEntity;
	
	
	
	public StudentEntity(){
		super();
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

	public void setFinalMarks(List<FinalMarkEntity> final_marks) {
		this.finalMarks = final_marks;
	}

	public List<ParentEntity> getParents() {
		return parents;
	}

	public void setParents(List<ParentEntity> parents) {
		this.parents = parents;
	}

	public ClassEntity getClassEntity() {
		return classEntity;
	}

	public void setClassEntity(ClassEntity classEntity) {
		this.classEntity = classEntity;
	}
	
	
	
	
	
}
