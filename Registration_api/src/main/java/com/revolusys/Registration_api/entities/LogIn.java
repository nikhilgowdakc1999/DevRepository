package com.revolusys.Registration_api.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogIn {

	@NotEmpty(message="email is mandatory field")
	@Email(message="provide a valid mailId")
	@Pattern(regexp="^.*\\.com$",message="Invalid mailId")
	private String Email;
	
//	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!#%&])[A-Za-z\\d@!&%#]+$",message="Invalid Password")
//	@Size(min=4,max=15,message="password must range b/n 4-10 ")
	@NotEmpty(message="password is mandatory field")
	private String password;
	
}
