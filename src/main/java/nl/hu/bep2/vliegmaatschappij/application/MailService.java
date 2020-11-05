package nl.hu.bep2.vliegmaatschappij.application;

import nl.hu.bep2.vliegmaatschappij.domein.Booking;
import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import nl.hu.bep2.vliegmaatschappij.domein.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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
		System.out.println(7);
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
			System.out.println(8);
			Transport.send(message);
			System.out.println(9);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void sendCreationmail(Booking booking){
		Flight flight = booking.getTravelClassFlight().getFlight();
		//original image: https://www.flaticon.com/free-icon/worlwide_655789
		StringBuilder mailBody = new StringBuilder("<html><img src=\"https://bep2.herokuapp.com/mailbanner.png\"<br>");
		mailBody.append(String.format("<h3>Booking to airport %s has been created!</h3>", flight.getRoute().getDestination().getName()));
		mailBody.append("<h4>Thank you for flying with V2B Flightservice.</h4>");
		mailBody.append("<h5>Flight details: </h5>");
		mailBody.append(String.format("Departure Airport: %s <br> Board till %s <br>", flight.getRoute().getDeparture().getName(), flight.getDepartureTime()));
		mailBody.append(String.format("Arrival: %s <br> Arrival at %s <br>", flight.getRoute().getDestination().getName(), flight.getArrivalTime()));
		mailBody.append("<h5>Passenger details: </h5><br>");

		int personcounter = 1;

		for(Person person : booking.getPersons()){
			mailBody.append(String.format("Traveler %s: %s %s Birthdate: %s<br>", personcounter, person.getFirstName(), person.getLastName(), person.getBirthday()));
			personcounter ++;
		}

		mailBody.append("<h5>Confirming your information: </h5><br>");
		String url=String.format("https://bep2.herokuapp.com/booking/confirm/%s", booking.getId());
		String link=String.format("<a href=\"%s\">%s</a>",url,url);
		mailBody.append(link).append("</html>");
		mailBody.append("<a href=\"https://bep2.herokuapp.com/redirect.html\">Confirm!</a>");
		System.out.println(5);
		sendMail(booking.getCustomer().getEmail(), "{V2B Flightservice} Booking created with destination: " + flight.getRoute().getDestination().getName(), mailBody.toString());
		System.out.println(6);
	}

	public void sendConfirmationmail(Booking booking){
		Flight flight = booking.getTravelClassFlight().getFlight();

		String mailBody = "<html><img src=\"https://bep2.herokuapp.com/mailbanner.png\"<br>" + String.format("<h3>Booking with destination %s has been confirmed.</h3>" + "Departure: %s at %s <br> Arrival: %s at %s <br>", flight.getRoute().getDestination().getName(),
				flight.getRoute().getDeparture().getName(),
				flight.getDepartureTime(),
				flight.getRoute().getDestination().getName(),
				flight.getArrivalTime()) +
				"<h4>Thank you for flying with V2B Flightservice.</h4>";
		mailService.sendMail(booking.getCustomer().getEmail(), "{V2B Flightservice} Confirmation of booking: " + flight.getRoute().getDestination().getName(), mailBody);
	}

}