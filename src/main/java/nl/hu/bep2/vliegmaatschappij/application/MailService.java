package nl.hu.bep2.vliegmaatschappij.application;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MailService {
	public static void sendMail(String to, String subject, String body) {
		String host = "smtp.gmail.com";
		final String user = "v2bflightservice@gmail.com";
		final String password = "Vl1egtuig.";

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", true);

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