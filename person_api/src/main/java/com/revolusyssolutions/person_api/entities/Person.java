package com.revolusyssolutions.person_api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="person_details")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	
	@NotEmpty(message="name is mandatory field")
	@Pattern(regexp="^[A-Z][a-zA-Z ]*$",message="name must start with capital and should contain only characters")
	private String name;

	
	@NotEmpty(message="email is mandatory field")
	@Email(message="provide a valid mailId")
	@Pattern(regexp="^.*\\.com$",message="Invalid mailId")
	//	@Pattern(regexp="^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message="invalid mail")
	private String email;

	
	//	@Size(min=21,max=60,message="age must be b/n 21-60")
	@Min(value=21,message="age must be at least 21")  
	@Max(value=59,message="age must be less than 60")
	private String age;

	
	@NotEmpty(message="gender is mandatory field")
	@Pattern(regexp="^(male|female|other)$",message="Invalid gender")
	private String gender;

	
	@NotEmpty(message="phoneno is mandatory field")
	@Size(min=10,max=10,message="phoneno must be a 10 digit number only")
	//	@Digits(fraction = 0, integer = 10, message="phoneno must be a 10 digit number only")
	private String phoneno;

	
	@NotEmpty(message="countryname is mandatory field")
	private String country;

	
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!#%&])[A-Za-z\\d@!&%#]+$",message="Invalid Password")
	@Size(min=4,max=15,message="password must range b/n 4-10 ")
	@NotEmpty(message="password is mandatory field")
	private String password;


	//setters and getters methods
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Person(int id,String name, String email, String age, String gender, String phoneno, String country,String password) {
		super();
		this.id=id;
		this.name = name;
		this.email = email;
		this.age = age;
		this.gender = gender;
		this.phoneno = phoneno;
		this.country = country;
		this.password=password;
	}
	public Person() {
		super();
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", email=" + email + ", age=" + age + ", gender=" + gender
				+ ", phoneno=" + phoneno + ", country=" + country + ", password=" + password + "]";
	}

}