package com.iktpreobuka.final_project.controllers.util;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.security.Views;

public class RESTError {
	
	@JsonView(Views.Private.class)
	private int code;
	
	@JsonView(Views.Private.class)
	private String message;
	
	public RESTError(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}

}
