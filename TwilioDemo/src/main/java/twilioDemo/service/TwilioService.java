package twilioDemo.service;

import java.util.List;

import twilioDemo.entity.LoginRequest;
import twilioDemo.entity.TwilioEntity;

public interface TwilioService {

	public TwilioEntity saveData(TwilioEntity twiliodata);
	
	public String login(LoginRequest request);
	
	public String ForgotPassword(LoginRequest request,String Otp);
	
	public List<TwilioEntity> getAllData();
	
	public String deletedata();
}
