package com.iktpreobuka.final_project.controllers.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iktpreobuka.final_project.entities.StudentEntity;

public class ClassDTO {
	
	//private List<ScheduleEntity> schedules=new ArrayList<ScheduleEntity>();
	@JsonProperty("students")
	private List<String> students=new ArrayList<String>();
	
	@NotNull(message="Head teacher must be provided.")
	@Min(value=1, message="Head teacher number must be greater than 0!")
	private String headTeacher;
	
	@NotNull(message="Class grade must be provided.")
	@Min(value=1, message="Grade must be between 1 and 8!")
	@Max(value=8, message="Grade must be between 1 and 8!")
	private String grade;

	@NotNull(message="Class number must be provided.")
	@Min(value=1, message="Class number must be between 1 and 4!")
	@Max(value=4, message="Class number must be between 1 and 4!")
	@JsonProperty("classNumber")
	private String classNumberStr;
	
	
	public ClassDTO() {
		super();
	}

	public String getHeadTeacher() {
		return headTeacher;
	}

	public void setHeadTeacher(String headTeacher) {
		this.headTeacher = headTeacher;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getClassNumberStr() {
		return classNumberStr;
	}

	public void setClassNumberStr(String classNumberStr) {
		this.classNumberStr = classNumberStr;
	}

	public List<String> getStudents() {
		return students;
	}

	public void setStudents(List<String> students) {
		this.students = students;
	}
	
	
	
	
	
	
	
	
	
	
}
