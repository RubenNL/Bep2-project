package nl.hu.bep2.vliegmaatschappij.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.*;
import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.*;
@Configuration
public class MailService {
	public static MailService mailService;
	@Value("${email.user}")
	private String user;
	@Value("${email.password}")
	private String password;
	@Value("${email.host}")
	private String host;

	private final Properties props = new Properties();

	@PostConstruct
	public void doSomethingAfterStartup() {
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", true);
		mailService=this;
	}
	public void sendMail(String to, String subject, String body) {

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
				});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setContent(body, "text/html");
			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}