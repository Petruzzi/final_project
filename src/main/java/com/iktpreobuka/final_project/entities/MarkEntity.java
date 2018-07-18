package com.iktpreobuka.final_project.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MarkEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView(Views.Private.class)
	private Integer id;
	
	@JsonView(Views.Private.class)
	private Byte markValue;
	
	@JsonView(Views.Admin.class)
	private Boolean locked;
	
	@JsonView(Views.Private.class)
	private Byte semester;
	
	
	@JsonView(Views.Private.class)
	@JsonFormat(
			shape=JsonFormat.Shape.STRING,
			pattern="dd-MM-yyyy",// hh:mm:ss
			timezone = "Europe/Belgrade")
	private Date dateRated;
	
	@JsonView(Views.Private.class)
	@JsonFormat(
			shape=JsonFormat.Shape.STRING,
			pattern="dd-MM-yyyy hh:mm:ss",
			timezone = "Europe/Belgrade")
	private Date lastUpdateDate;
	
	
	@JsonView(Views.Private.class)
	private String description;
	
	
	
	@Version
	@JsonIgnore
	private Integer version;
	

	
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="student")
	@JsonView(Views.Private.class)
	private StudentEntity student;
	
	
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="schedule")
	@JsonView(Views.Private.class)
	private ScheduleEntity schedule;
	
	
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="markType")
	@JsonView(Views.Private.class)
	private MarkTypeEntity markType;
	
	
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="ratedBy")
	@JsonView(Views.Private.class)
	private ProfessorEntity ratedBy;

	public MarkEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Byte getMarkValue() {
		return markValue;
	}

	public void setMarkValue(Byte markValue) {
		this.markValue = markValue;
	}

	public Date getDateRated() {
		return dateRated;
	}

	public void setDateRated(Date dateRated) {
		this.dateRated = dateRated;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public ScheduleEntity getSchedule() {
		return schedule;
	}

	public void setSchedule(ScheduleEntity schedule) {
		this.schedule = schedule;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public MarkTypeEntity getMarkType() {
		return markType;
	}

	public void setMarkType(MarkTypeEntity markType) {
		this.markType = markType;
	}

	public ProfessorEntity getRatedBy() {
		return ratedBy;
	}

	public void setRatedBy(ProfessorEntity ratedBy) {
		this.ratedBy = ratedBy;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Byte getSemester() {
		return semester;
	}

	public void setSemester(Byte semester) {
		this.semester = semester;
	}
	

}	
