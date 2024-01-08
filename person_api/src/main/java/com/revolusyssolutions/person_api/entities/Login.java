package com.revolusyssolutions.person_api.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Login {

	@NotEmpty(message="email is mandatory field")
	@Email(message="provide a valid mailId")
	@Pattern(regexp="^.*\\.com$",message="Invalid mailId")
	private String Email;
	
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!#%&])[A-Za-z\\d@!&%#]+$",message="Invalid Password")
	@Size(min=4,max=15,message="password must range b/n 4-10 ")
	@NotEmpty(message="password is mandatory field")
	private String Password;
	
	
	@Override
	public String toString() {
		return "Login [Email=" + Email + ", Password=" + Password + "]";
	}
	public Login() {
		super();
		
	}
	public Login(String email, String password) {
		super();
		Email = email;
		Password = password;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
}
