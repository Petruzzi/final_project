package com.iktpreobuka.final_project.controllers.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.iktpreobuka.final_project.enumeration.SubjectStatusEnum;

public class SubjectGradeDTO {

	
	@NotNull(message="Subject status must be provided.")
	//@JsonView(Views.Private.class)
	private SubjectStatusEnum subjectStatus;//izborni ili ne
	
	@NotNull(message="\"Affect average\" field must be provided.")
	//@JsonView(Views.Private.class)
	private Boolean affectAvg;//da li utice na prosek
	
	@NotNull(message="Class load must be provided.")
	@Min(value=1, message="Class load must be greater than 0!")
	//@JsonView(Views.Private.class)
	private String classLoadStr;// fond casova
	
	@NotNull(message="Subject must be provided.")
	@Min(value=1, message="Subject id must be greater than 0!")
	private String subjectStr;
	
	@NotNull(message="Grade must be provided.")
	@Min(value=1, message="Grade id must be greater than 0!")
	private String gradeStr;


	public SubjectGradeDTO() {
		super();
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




	public String getClassLoadStr() {
		return classLoadStr;
	}


	public void setClassLoadStr(String classLoadStr) {
		this.classLoadStr = classLoadStr;
	}


	public String getSubjectStr() {
		return subjectStr;
	}


	public void setSubjectStr(String subjectStr) {
		this.subjectStr = subjectStr;
	}


	public String getGradeStr() {
		return gradeStr;
	}


	public void setGradeStr(String gradeStr) {
		this.gradeStr = gradeStr;
	}

	
	
}
