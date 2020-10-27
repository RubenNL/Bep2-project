package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Booking {
	@Id
	@GeneratedValue
	private int id;
	@ManyToMany
	private List<Person> persons;
	@ManyToOne
	private Customer customer;
	@ManyToMany
	private List<TravelClassFlight> travelClassFlightList;
	private double price;
}