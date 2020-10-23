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
	@OneToOne
	private Customer customer;
	@OneToOne
	private Price price;
	@ManyToMany
	private List<TravelClassFlight> travelClassFlightList;
}