package com.iktpreobuka.final_project.controllers.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidation{	

	public String generatePass() {
		Random r=new Random();
		String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String str ="";
		
		for(byte i=0 ; i<4 ; i++) 
			str+=abc.charAt(r.nextInt(abc.length()));
		
		return str;
	}

	public String passwordCheck (String fromDB ,String old,String newPass,String newPassRep) {
		if(Encryption.encodedPasswordMatch(old,fromDB)) {
			if(!newPass.equals(newPassRep)) 
				return "New password don`t match";
		}else 
			return "Wrong password!";
		return null;
	}
	
	
	

public static void main(String[] args) {
		PasswordValidation pv=new PasswordValidation();
		
	System.out.println(pv.passwordCheck("$2a$10$QYA9OipjS6rSYKlt3y8jDOhTI/zjFosvY1IVbe3k6V8BNi5IvLJ8i", "1234", "test", "test"));
	}
}
	