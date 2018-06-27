package com.iktpreobuka.final_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParentEntity extends UserEntity{
	
	

	@ManyToMany(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinTable(name="parent_student",joinColumns=
			{@JoinColumn(name="parent_id",nullable=false,updatable=false)},
		inverseJoinColumns=
			{@JoinColumn(name="student_id",nullable=false,updatable=false)})
	@JsonView(Views.Private.class)
	List<StudentEntity> students=new ArrayList<StudentEntity>();
	
	
	public ParentEntity() {
		super();
	}


	public List<StudentEntity> getStudents() {
		return students;
	}


	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}
	

}
