package com.nc.med.mail;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${email.from.address}")
	private String fromAddress;

	@Value("${email.receiver.username}")
	private String username;

	@Scheduled(cron = "10 02 23 * * ?")
	public void sendMail() throws MessagingException {
		sendMailMultipart(username, "Subject", "Mail body", null);
	}

	@Scheduled(cron = "10 12 23 * * ?")
	public void sendMailAttachment() throws MessagingException {
		File file = new File("/Users/niteenchougula/Downloads/AMTM/Task Notes.txt");
		sendMailMultipart(username, "Subject", "Mail body", file);
	}

	public void sendMailMultipart(String toEmail, String subject, String message, File file) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom(fromAddress);
		helper.setTo(toEmail);
		helper.setSubject(subject);
		helper.setText(message);

		if (file != null) {
			helper.addAttachment(file.getName(), file);
		}
		javaMailSender.send(mimeMessage);
	}
}
