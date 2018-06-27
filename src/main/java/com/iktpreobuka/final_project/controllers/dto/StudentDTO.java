package com.iktpreobuka.final_project.controllers.dto;

import java.util.ArrayList;
import java.util.List;

public class StudentDTO extends UserDTO {
	
	List<String> parentIdsStr=new ArrayList<String>();
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
