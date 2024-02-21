package com.revolusyssolutions.person_api.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Login {

	@NotEmpty(message="name is mandatory field")
	@Pattern(regexp="^[A-Z][a-zA-Z ]*$",message="name must start with capital and should contain only characters")
	private String name;
	
	@NotEmpty(message="email is mandatory field")
	@Email(message="provide a valid mailId")
	@Pattern(regexp="^.*\\.com$",message="Invalid mailId")
	//	@Pattern(regexp="^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message="invalid mail")
	private String email;

	@NotEmpty(message="password is mandatory field")
	private String password;
}
