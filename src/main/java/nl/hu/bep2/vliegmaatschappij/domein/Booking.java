package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.hu.bep2.vliegmaatschappij.application.MailService;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Booking {
	@Id
	@GeneratedValue
	private int id;
	@ManyToMany(cascade= CascadeType.ALL)
	private List<Person> persons;
	@ManyToOne(cascade=CascadeType.ALL)
	private Customer customer;
	@ManyToOne
	private TravelClassFlight travelClassFlight;
	private double price;
	private boolean confirmed;
	@PrePersist
	private void calculatePrice(){
		price = travelClassFlight.calculatePrice() * persons.size();
	}
	@PostPersist
	private void sendCreationmail(){
		Flight flight = travelClassFlight.getFlight();
		StringBuilder mailBody = new StringBuilder(String.format("<h3>Booking %s has been created!</h3>", id));
		mailBody.append("<h4>Thank you for flying with V2B Flightservice.</h4>");
		mailBody.append("<h5>Flight details: </h5>");
		mailBody.append(String.format("Departure Airport: %s <br> Board till %s <br>", flight.getRoute().getDeparture().getName(), flight.getDepartureTime()));
		mailBody.append(String.format("Arrival: %s <br> Arrival at %s <br>", flight.getRoute().getDestination().getName(), flight.getArrivalTime()));
		mailBody.append("<h5>Passenger details: </h5><br>");
		int personcounter = 1;

		for(Person person : persons){
			mailBody.append(String.format("Traveler %s: %s %s Birthdate: %s<br>", personcounter, person.getFirstName(), person.getLastName(), person.getBirthday()));
			personcounter ++;
		}

		mailBody.append("<h5>Confirming your information: </h5><br>");
		mailBody.append(String.format("https://https://bep2.herokuapp.com/booking/confirm/%s", id));

		MailService.mailService.sendMail(customer.getEmail(), "{V2B Flightservice} Booking created: " + id, mailBody.toString());
	}

	public Booking() {
		confirmed = false;
	}
}