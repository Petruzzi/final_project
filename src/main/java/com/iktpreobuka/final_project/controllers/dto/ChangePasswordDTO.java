package com.iktpreobuka.final_project.controllers.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePasswordDTO {
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull(message = "Old password must be provided.")
	@Size(min=3, max=15, message = "Old password must be between {min} and {max} characters long.")
	private String oldPassword;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull(message = "New password must be provided.")
	@Size(min=3, max=15, message = "New password must be between {min} and {max} characters long.")
	private String newPassword;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull(message = "New password must be repeated.")
	@Size(min=3, max=15, message = "New password must be between {min} and {max} characters long.")
	private String repeatNewPassword;
	
	public ChangePasswordDTO() {
		super();
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatNewPassword() {
		return repeatNewPassword;
	}

	public void setRepeatNewPassword(String repeatNewPassword) {
		this.repeatNewPassword = repeatNewPassword;
	}
	
	
	
	
	
	
}
