package twilioDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import twilioDemo.entity.LoginRequest;
import twilioDemo.entity.TwilioEntity;
import twilioDemo.service.TwilioServiceImpl;

@RestController
public class TwilioController {
	
	@Autowired
	private TwilioServiceImpl service;
	
	@PostMapping("/save")
	public TwilioEntity addData(@RequestBody TwilioEntity entity)
	{
		return this.service.saveData(entity);
	}
	
	@PostMapping("/login")
	public String loginData(@RequestBody LoginRequest log)
	{
	String msg=this.service.login(log);
	return msg;
	}
	
	@PutMapping("/reset-Password")
	public String resetPassword(@RequestBody LoginRequest log,@RequestParam String Otp)
	{
		String msg=this.service.ForgotPassword(log, Otp);
		return msg;
	}
	
	@GetMapping("/getData")
	public List<TwilioEntity> fetchAll()
	{
		List<TwilioEntity> data=this.service.getAllData();
		return data;
	}
	
	@DeleteMapping("/deleteData")
	public String deleteAll()
	{
		return this.service.deletedata();
	}

}
