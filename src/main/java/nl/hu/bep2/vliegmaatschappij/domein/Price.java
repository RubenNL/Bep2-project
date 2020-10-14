package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Price {
	@Id
	@GeneratedValue
	private int id;
	private double price;

	public Price() {

	}

	public Price(Planetype type, TravelClass travelClass, Flightroute route) {
		this.price = calculatePrice(type, travelClass, route);
	}

	public double calculatePrice(Planetype type, TravelClass travelClass, Flightroute route) {
		// TODO create function
		return 0.0;
	}

	public double getPrice() {
		return price;
	}

	public int getId() {
		return id;
	}
}