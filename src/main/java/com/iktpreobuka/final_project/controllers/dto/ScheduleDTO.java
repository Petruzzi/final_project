package com.iktpreobuka.final_project.controllers.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScheduleDTO {

	@NotNull(message="Teacher must be provided.")
	@Min(value=1, message="Teacher id number must be greater than 0!")
	@JsonProperty("teacher")
	private String teacherStr;

	@NotNull(message="Subject must be provided.")
	@Min(value=1, message="Subject id number must be greater than 0!")
	@JsonProperty("subject")
	private String subjectStr;
	
	@NotNull(message="Class must be provided.")
	@Min(value=1, message="Class id number must be greater than 0!")
	@JsonProperty("classEntity")
	private String classEntityStr;
	
	@NotNull(message="School year must be provided.")
	@Min(value=1, message="School year number must be greater than 0!")
	@JsonProperty("schoolYear")
	private String schoolYearStr;

	public ScheduleDTO() {
		super();
	}

	public String getTeacherStr() {
		return teacherStr;
	}

	public void setTeacherStr(String teacherStr) {
		this.teacherStr = teacherStr;
	}

	public String getSubjectStr() {
		return subjectStr;
	}

	public void setSubjectStr(String subjectStr) {
		this.subjectStr = subjectStr;
	}

	public String getClassEntityStr() {
		return classEntityStr;
	}

	public void setClassEntityStr(String classEntityStr) {
		this.classEntityStr = classEntityStr;
	}

	public String getSchoolYearStr() {
		return schoolYearStr;
	}

	public void setSchoolYearStr(String schoolYearStr) {
		this.schoolYearStr = schoolYearStr;
	}
	
	
	
	
	
	
	
	
	
	
}
