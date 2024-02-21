package otpVerificationDemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	JavaMailSender javaMailSender; // interface for sending emails

	public void sendEmail(String to, String subject, String body) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage(); //MimeMessage is a class in JavaMail API MIME=>Multipurpose Internet Mail Extensions
			MimeMessageHelper helper = new MimeMessageHelper(message, true);//MimeMessageHelper is used to handle multipart messages
			helper.setTo(to); // set the recipient email address
			helper.setSubject(subject); // set the email subject
			helper.setText(body); // set the email body
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
