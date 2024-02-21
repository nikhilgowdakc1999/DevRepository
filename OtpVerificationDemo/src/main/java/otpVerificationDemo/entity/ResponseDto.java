package otpVerificationDemo.entity;

import lombok.Data;

@Data
public class ResponseDto {

	private int id;
	private String name;
	private String email;
	private String password;
	private String message;
	
}
