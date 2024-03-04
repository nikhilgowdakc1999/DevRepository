package twilioDemo.service;


import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import twilioDemo.entity.LoginRequest;
import twilioDemo.entity.TwilioEntity;
import twilioDemo.repository.TwilioRepo;

@Service
public class TwilioServiceImpl implements TwilioService {

	@Autowired
	private TwilioRepo repo;
	
	@Autowired
	private TwilioConfig config;
	
	String generatedOtp;
	
	@Override
	public TwilioEntity saveData(TwilioEntity twiliodata) {
		return this.repo.save(twiliodata);	
	}
	
	@Override
	public String login(LoginRequest request) {
	       Optional<TwilioEntity> existingRecord=repo.findByname(request.getName());
	       if(existingRecord.isPresent())
	       {
	    	   if(request.getPassword().equals(existingRecord.get().getPassword()))
	    	   {
	    		   Random r = new Random();
					generatedOtp = String.format("%06d", r.nextInt(100000));
					
					//Send Otp to mobile
					PhoneNumber to=new PhoneNumber(existingRecord.get().getPhoneNumber());
					PhoneNumber from=new PhoneNumber(config.getTrialNumber());
					String body="Your verification OTP is "+generatedOtp;
					Message msg=Message.creator(to, from,body).create();
					
					return "Login Succesfull!! && OTP : "+generatedOtp;
	    	   }
	    	   else
	    	   {
	    		   throw new RuntimeException("Password incorrect!!");
	    	   }  
	       }else
	       {
	    	   throw new RuntimeException("User doesn't exist with provided name!!");
	       }        
	}

	@Override
	public String ForgotPassword(LoginRequest request,String otp) {
		Optional<TwilioEntity> existingperson=repo.findByname(request.getName());
		if(existingperson.isPresent()) 
		{
			if(generatedOtp.equals(otp))
			{
				existingperson.get().setPassword(request.getPassword());;
				TwilioEntity per=repo.save(existingperson.get());
				return "Password changed succesfully";
			}
			else
			{
				throw new RuntimeException("Invalid OTP provided!!");
			}
			
		}
		else 
		{
			throw new RuntimeException("User doesn't exist with provided email!!");
		}	
	}
	

	@Override
	public String deletedata() {
		repo.deleteAll();
		generatedOtp=null;
		return "All records deleted!!";
	}

	@Override
	public List<TwilioEntity> getAllData() {
		List<TwilioEntity> data=repo.findAll();
		return data;
	}

}
