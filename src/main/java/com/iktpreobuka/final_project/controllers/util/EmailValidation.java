package com.iktpreobuka.final_project.controllers.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {
	
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
	        return matcher.find();
	}
		
		
		
		
		
}
