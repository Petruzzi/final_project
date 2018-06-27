package com.iktpreobuka.final_project.controllers.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SchoolYearDTO {

	@NotNull(message="School year start date must be provided..")
	@Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}",message="Date format must be 11-11-2011.")
	private String startDateStr;
	
	@NotNull(message="School year semester date must be provided..")
	@Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}",message="Date format must be 11-11-2011.")
	private String semesterDateStr;
	
	@NotNull(message="School year end date must be provided..")
	@Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}",message="Date format must be 11-11-2011.")
	private String endDateStr;

	public SchoolYearDTO() {
		super();
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getSemesterDateStr() {
		return semesterDateStr;
	}

	public void setSemesterDateStr(String semesterDateStr) {
		this.semesterDateStr = semesterDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	
	
	
}

