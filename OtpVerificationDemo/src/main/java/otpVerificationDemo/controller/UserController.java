package otpVerificationDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import otpVerificationDemo.entity.Login;
import otpVerificationDemo.entity.ResponseDto;
import otpVerificationDemo.entity.UserData;
import otpVerificationDemo.service.UserDataService;

@RestController
public class UserController {

	@Autowired
	private UserDataService userService;
	
	@GetMapping("/getUsers")
	public List<UserData>  getAllUsers()
	{
		List<UserData> users=this.userService.getAll() ;
		return users;
	}
	
	@PostMapping("/save")
	public ResponseDto createUser(@RequestBody UserData user )
	{
		ResponseDto saveduser=this.userService.addUser(user);	
		return saveduser;
	}
	
	@DeleteMapping("/deleteUsers")
	public String deleteAllUsers()
	{
		String msg=this.userService.deleteAllUsers();
		return msg;
	}
	
	@PostMapping("/login")
	public String userLogin(@RequestBody Login log, @RequestParam String otp)
	{
		String msg=userService.UserLogin(log, otp);
		return msg;
	}
	
	@PutMapping("/reset-password")
	public String resetPassword(@RequestBody Login log,@RequestParam String otp)
	{
		String msg=userService.updatePassword(log,otp);
		return msg;
	}
	
	@PostMapping("/generateOtp")
	public String generateOtp(@RequestBody UserData request)
	{
		String otp=this.userService.generateOTP(request);
		return otp;
	}
	
}
