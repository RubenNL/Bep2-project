package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

	public Booking() {
		confirmed = false;
	}
}