package nl.hu.bep2.vliegmaatschappij.domein;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import nl.hu.bep2.vliegmaatschappij.application.MailService;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class Booking {
	@Id
	@GeneratedValue(generator= UUIDGenerator.generatorName)
	@GenericGenerator(name = UUIDGenerator.generatorName, strategy = "nl.hu.bep2.vliegmaatschappij.domein.UUIDGenerator")
	private String id;
	@ManyToMany(cascade= CascadeType.ALL)
	private List<Person> persons;
	@ManyToOne(cascade=CascadeType.ALL)
	@JsonIgnore
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