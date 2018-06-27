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
public class AbsenceRecordEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView(Views.Admin.class)
	private Integer id;
	
	@JsonView(Views.Private.class)
	@JsonFormat(
			shape=JsonFormat.Shape.STRING,
			pattern="dd-MM-yyyy hh:mm:ss")
	private Date date;
	
	@JsonView(Views.Private.class)
	private String description;
	
	@Version
	@JsonIgnore
	private Integer version;
	
	
//	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
//	@JoinColumn(name="schoolYear")
//	@JsonView(Views.Private.class)
//	private SchoolYearEntity schoolYear;
//	
	
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="schedule")
	@JsonView(Views.Private.class)
	private ScheduleEntity schedule;
	
	
	
	public AbsenceRecordEntity() {
		super();
	}
	
	
}
