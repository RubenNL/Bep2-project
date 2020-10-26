package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Price {
	@Id
	@GeneratedValue
	private int id;
	private double price;

	public Price(TravelClass travelClass, FlightRoute route) {
		this.price = calculatePrice(travelClass, route);
	}

	public double calculatePrice(TravelClass travelClass, FlightRoute route) {
		// TODO create function
		return 0.0;
	}
}
