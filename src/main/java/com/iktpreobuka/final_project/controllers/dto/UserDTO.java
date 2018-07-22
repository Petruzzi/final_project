package com.iktpreobuka.final_project.controllers.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDTO {


	@NotNull(message="First name must be provided.")
	@Size(min=3, max=15, message = "First name must be between {min} and {max} characters long.")
	@Pattern(regexp = "^[A-Z]{1}[a-z]+$",message="First name format must be first letter uppercase then lowercase(e.g. Nsame)")
	private String name;
	
	@NotNull(message="Last name must be provided.")
	@Size(min=3,max=15,message="Last name must be between {min} and {max} characters long.")
	@Pattern(regexp = "^[A-Z]{1}[a-z]+$",message="Last name format must be first letter uppercase then lowercase(e.g. Lastname)" )
	private String lastname;
	
	@NotNull(message="Username must be provided.")
	@Size(min=3,max=15,message="Username must be between {min} and {max} characters long.")
	private String username;
	
	@NotNull(message = "Email must be provided.")
	@Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$",message="Email must be exemple@gmail.com.")
	private String email;

	public UserDTO() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public RoleEntity getRole() {
//		return role;
//	}
//
//	public void setRole(RoleEntity role) {
//		this.role = role;
//	}
//


}
