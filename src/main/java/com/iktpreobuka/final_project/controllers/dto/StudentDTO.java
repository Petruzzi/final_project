package com.iktpreobuka.final_project.controllers.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentDTO extends UserDTO {
	
	@JsonProperty("parents")
	List<String> parentIdsStr=new ArrayList<String>();
	
	@JsonProperty("class")
	private String classIdStr;
	
	public StudentDTO() {
		super();
	}

	public List<String> getParentIdsStr() {
		return parentIdsStr;
	}

	public void setParentIdsStr(List<String> parentIdsStr) {
		this.parentIdsStr = parentIdsStr;
	}

	public String getClassIdStr() {
		return classIdStr;
	}

	public void setClassIdStr(String classIdStr) {
		this.classIdStr = classIdStr;
	}




	
	
	
}
