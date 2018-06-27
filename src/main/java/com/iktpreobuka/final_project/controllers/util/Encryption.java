package com.iktpreobuka.final_project.controllers.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryption {
	
	public static String getPassEncoded(String pass) {
		BCryptPasswordEncoder bCryptPasswordEncoder =new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(pass);
	}
		
	public static Boolean encodedPasswordMatch(String passRow, String passEncoded) {
		BCryptPasswordEncoder bCryptPasswordEncoder =new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.matches(passRow, passEncoded);
	}
	
	public static void main(String[] args) {
		System.out.println(getPassEncoded("1234"));
	}
	
}
