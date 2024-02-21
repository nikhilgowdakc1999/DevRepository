package otpVerificationDemo.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import otpVerificationDemo.entity.Login;
import otpVerificationDemo.entity.ResponseDto;
import otpVerificationDemo.entity.UserData;
import otpVerificationDemo.repository.UserRepository;

@Service
public class UserDataService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	EmailService emailService;   //imp

	String generatedOtp;         //imp


	public ResponseDto addUser(UserData user)
	{
		ResponseDto res = new ResponseDto();

		Optional<UserData> storedvalue=userRepo.findByemail(user.getEmail());
		if(storedvalue.isPresent())
		{
			res.setMessage("Record already present!!");
		}
		else
		{
			UserData savedUser=this.userRepo.save(user);

			res.setEmail(savedUser.getEmail());
			res.setId(savedUser.getId());
			res.setName(savedUser.getName());
			res.setPassword(savedUser.getPassword());
			res.setMessage("Record added succesfully!!");	
		}
		return res;
	}

	public String generateOTP(UserData request)  //imp
	{
		Optional<UserData> existingUser = this.userRepo.findByemail(request.getEmail());
		if(existingUser.isPresent())
		{
			Random r = new Random();
			generatedOtp = String.format("%06d", r.nextInt(100000)); // generates random nos in b/n 0-99999
            // % -> start of format specifier,0-> 'O'padding on left,6-> width of characters,d-> conversion character for a Decimal Integer
			String subject = "Email Verfication";
			String body = "Your verification OTP is "+generatedOtp;
			//Email Send
			this.emailService.sendEmail(existingUser.get().getEmail(), subject, body);

			return "Generated Otp : "+generatedOtp;
		}else
		{
			return "User doesn't exist for provided mail!!";
		}

	}

	public String UserLogin(Login log,String otp) { //imp

		Optional<UserData> existingRecord=userRepo.findByemail(log.getEmail());
		if(existingRecord.isPresent())
		{
			if(otp.equals(generatedOtp) && log.getPassword().equals(existingRecord.get().getPassword()) )
			{
				return "Login Succesfull!!";	 
			}else
			{
				return "Password incorrect or OTP Invalid!!";
			}	
		}else
		{
			return "User doesn't exist with provided email!!";
		}

	}

	public String updatePassword(Login log,String otp)  //imp
	{
		Optional<UserData> existingperson=userRepo.findByemail(log.getEmail());
		if(existingperson.isPresent()) 
		{
			if(generatedOtp.equals(otp))
			{
				existingperson.get().setPassword(log.getPassword());
				userRepo.save(existingperson.get());
				return "Password changed succesfully!!";
			}else
			{
				return "Invalid OTP provided!!";
			}	
		}
		else 
		{
			return "User doesn't exist with provided email!!";
		}	
	}

	public List<UserData> getAll()
	{
		List<UserData> users=this.userRepo.findAll();
		System.out.println("All Persons list :"+users);
		return users;
	}

	public String deleteAllUsers()
	{
		userRepo.deleteAll();	
		generatedOtp=null;
		System.out.println("otp= "+generatedOtp);
		return "All records Deleted!!";
	}

}
