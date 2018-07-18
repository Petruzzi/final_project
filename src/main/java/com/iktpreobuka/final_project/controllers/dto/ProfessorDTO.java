package com.iktpreobuka.final_project.controllers.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfessorDTO extends UserDTO {
	
	@JsonProperty("subjects")
	List<String> subjectIdsStr=new ArrayList<String>();

	public ProfessorDTO() {
		super();
	}

	public List<String> getSubjectIdsStr() {
		return subjectIdsStr;
	}

	public void setSubjectIdsStr(List<String> subjectIdsStr) {
		this.subjectIdsStr = subjectIdsStr;
	}
	
	
}
