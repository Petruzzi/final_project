package com.iktpreobuka.final_project.controllers.dto;

import java.util.ArrayList;
import java.util.List;

public class ParentDTO extends UserDTO {


	
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
