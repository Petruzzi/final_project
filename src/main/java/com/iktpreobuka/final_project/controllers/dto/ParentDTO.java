package com.iktpreobuka.final_project.controllers.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParentDTO extends UserDTO {


	@JsonProperty("students")
	List<String> studentIdsStr=new ArrayList<String>();

	public ParentDTO() {
		super();
	}

	public List<String> getStudentIdsStr() {
		return studentIdsStr;
	}

	public void setStudentIdsStr(List<String> studentIdsStr) {
		this.studentIdsStr = studentIdsStr;
	}


	
	
}
