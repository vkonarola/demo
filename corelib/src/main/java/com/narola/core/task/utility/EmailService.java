package com.narola.core.task.utility;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Async
	public void send(String htmlContent, String subject, String toEmail, String fromName) {
		try {
			MimeMessage message = this.emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(new InternetAddress("no-reply@dailytaskservice.narola", fromName));
			helper.setTo(toEmail);
			helper.setSubject(subject);
			helper.setText(htmlContent, Boolean.TRUE);
			this.emailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
