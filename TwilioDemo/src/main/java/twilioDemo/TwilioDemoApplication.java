package twilioDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;
import twilioDemo.service.TwilioConfig;

@SpringBootApplication
public class TwilioDemoApplication {

	@Autowired
	private TwilioConfig twilioConfig;

	@PostConstruct
	public void initTwilio(){
		Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
	}
	public static void main(String[] args) {
		SpringApplication.run(TwilioDemoApplication.class, args);
	}

}
