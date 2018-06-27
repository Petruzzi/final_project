package com.iktpreobuka.final_project.controllers.dto;

public class AvgMarkDTO {
	private Integer studentID;
	private Integer scheduleID;
	private Double avg;
	
	public AvgMarkDTO() {
		super();
	}
	
	
	public AvgMarkDTO(Integer studentID, Integer scheduleID, Double avg) {
		super();
		this.studentID = studentID;
		this.scheduleID = scheduleID;
		this.avg = avg;
	}


	public Integer getStudentID() {
		return studentID;
	}
	public void setStudentID(Integer studentID) {
		this.studentID = studentID;
	}
	public Integer getScheduleID() {
		return scheduleID;
	}
	public void setScheduleID(Integer scheduleID) {
		this.scheduleID = scheduleID;
	}
	public Double getAvg() {
		return avg;
	}
	public void setAvg(Double avg) {
		this.avg = avg;
	}
	
	
}
