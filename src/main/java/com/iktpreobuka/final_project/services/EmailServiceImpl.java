package com.iktpreobuka.final_project.services;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.controllers.util.EmailObject;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	public JavaMailSender emailSender;
	
	@Override
	public void sendSimpleMessage(EmailObject obj) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(obj.getTo());
		message.setSubject(obj.getSubject());
		message.setText(obj.getText());
		emailSender.send(message);	
	}

	@Override
	public String textTemplateMark(String studentName ,Byte mark ,String subject, Date date ,String professor)throws Exception {
		
			Format formatter = new SimpleDateFormat("dd-MM-yyyy");
			String dateStr = formatter.format(date);
			
			String str="Postovani, obavestavamo vas da je ucenik "+studentName+
					" dobio ocenu "+mark+", iz predmeta " +subject+", datuma "+dateStr+
					" od strane profesora "+professor;
			return str;
		
	}
	
	public String textTemplatePass(String pass){
		String text="Postovani, sifra vaseg naloga je \""+pass+"\"" ;
		return text;
	}
}
