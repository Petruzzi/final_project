package com.iktpreobuka.final_project.services;

import java.util.Date;

import com.iktpreobuka.final_project.controllers.util.EmailObject;

public interface EmailService {
	public void sendSimpleMessage(EmailObject obj);
	
	public String textTemplateMark(String studentName ,Byte mark ,String subject, Date date,String professor)throws Exception;

	public String textTemplatePass(String pass);
}
