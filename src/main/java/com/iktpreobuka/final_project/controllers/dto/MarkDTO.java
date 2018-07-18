package com.iktpreobuka.final_project.controllers.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarkDTO {

	
	@NotNull(message="Mark value must be provided.")
	@Min(value=1, message="Mark value must be between 1 and 5!")
	@Max(value=5, message="Mark value must be between 1 and 5!")
	@JsonProperty ("markValue")
	private String markValueStr;
	
	@Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}",message="Date format must be 11-11-2011.")
//	@JsonFormat(
//			shape=JsonFormat.Shape.STRING,
//			//pattern="dd-MM-yyyy",// hh:mm:ss
//			timezone = "Europe/Belgrade")
	private String dateRated;
	//private Date dateRated;
	
	
	@Size(min=0, max=50, message = "Description must be between {min} and {max} characters long.")
	@JsonProperty ("description")
	private String descriptionStr;
	
	@NotNull(message="Student must be provided.")
	@Min(value=1, message="Student number must be greater than 0!")
	@JsonProperty ("student")
	private String studentStr;
	
	@NotNull(message="Schedule must be provided.")
	@Min(value=1, message="Schedule number must be greater than 0!")
	@JsonProperty ("schedule")
	private String scheduleStr;
	
	@NotNull(message="Type must be provided.")
	@Min(value=1, message="Type number must be greater than 0!")
	@JsonProperty ("markType")
	private String markTypeStr;

	public MarkDTO() {
		super();
	}

	public String getMarkValueStr() {
		return markValueStr;
	}

	public void setMarkValueStr(String markValueStr) {
		this.markValueStr = markValueStr;
	}

//	public Date getDateRated() {
//		return dateRated;
//	}
//
//	public void setDateRated(Date dateRated) {
//		this.dateRated = dateRated;
//	}

	public String getDescriptionStr() {
		return descriptionStr;
	}

	public void setDescriptionStr(String descriptionStr) {
		this.descriptionStr = descriptionStr;
	}

	public String getStudentStr() {
		return studentStr;
	}

	public void setStudentStr(String studentStr) {
		this.studentStr = studentStr;
	}

	public String getScheduleStr() {
		return scheduleStr;
	}

	public void setScheduleStr(String scheduleStr) {
		this.scheduleStr = scheduleStr;
	}

	public String getMarkTypeStr() {
		return markTypeStr;
	}

	public void setMarkTypeStr(String markTypeStr) {
		this.markTypeStr = markTypeStr;
	}

	public String getDateRated() {
		return dateRated;
	}

	public void setDateRated(String dateRated) {
		this.dateRated = dateRated;
	}

	

	
	
	
	
	
	
	
}
