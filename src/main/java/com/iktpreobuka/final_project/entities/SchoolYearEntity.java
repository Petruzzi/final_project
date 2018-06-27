package com.iktpreobuka.final_project.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.security.Views;
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SchoolYearEntity {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView(Views.Admin.class)
	private Integer id;
	
	


	@JsonView(Views.Admin.class)
	@JsonFormat(
			shape=JsonFormat.Shape.STRING,
			pattern="dd-MM-yyyy",
			timezone = "Europe/Belgrade")
	private Date startDate;
	

	@JsonView(Views.Admin.class)
	@JsonFormat(
			shape=JsonFormat.Shape.STRING,
			pattern="dd-MM-yyyy",
			timezone = "Europe/Belgrade")
	private Date endDate;
	

	@JsonView(Views.Admin.class)
	@JsonFormat(
			shape=JsonFormat.Shape.STRING,
			pattern="dd-MM-yyyy",
			timezone = "Europe/Belgrade")
	private Date semesterDate;
	
	
	
	@JsonView(Views.Private.class)
	private String schoolYearName;
	
	
	@JsonView(Views.Admin.class)
	private Boolean active;
	
	@Version
	@JsonIgnore
	private Integer version;
	
	
	@JsonIgnore
	@OneToMany(mappedBy="schoolYear",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	private List<ScheduleEntity> schedules=new ArrayList<ScheduleEntity>();
	
	
//	@JsonIgnore
//	@OneToMany(mappedBy="schoolYear",fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
//	private List<AbsenceRecordEntity> absenceRecord=new ArrayList<AbsenceRecordEntity>();
//	
	
	
	public SchoolYearEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getSemesterDate() {
		return semesterDate;
	}

	public void setSemesterDate(Date semesterDate) {
		this.semesterDate = semesterDate;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<ScheduleEntity> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<ScheduleEntity> schedules) {
		this.schedules = schedules;
	}

	public String getSchoolYearName() {
		return schoolYearName;
	}

	public void setSchoolYearName(String schoolYearName) {
		this.schoolYearName = schoolYearName;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}


	
}
