package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.hu.bep2.vliegmaatschappij.application.MailService;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public Booking() {
		confirmed = false;
	}
}