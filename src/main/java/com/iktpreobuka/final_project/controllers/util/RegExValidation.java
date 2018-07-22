package com.iktpreobuka.final_project.controllers.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExValidation {
	
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$", Pattern.CASE_INSENSITIVE);

	public static boolean validateEmail(String emailStr) {
	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
	        return matcher.find();
	}
		
		
	private static final Pattern VALID_FIRST_LETTER_REGEX = Pattern.compile("^[A-Z]{1}[a-z]+$");

	public static boolean validateFirstLetter(String str) {
	        Matcher matcher = VALID_FIRST_LETTER_REGEX .matcher(str);
	        return matcher.find();
	}
		
	
	
	
	public static void main(String[] args) {
		
		System.out.println(RegExValidation.validateFirstLetter("test"));
		System.out.println(RegExValidation.validateEmail("test"));
	}
		
}
