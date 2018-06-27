package com.iktpreobuka.final_project.controllers.dto;

import java.util.ArrayList;
import java.util.List;

public class ProfessorDTO extends UserDTO {
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
