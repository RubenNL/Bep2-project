package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TravelClass {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private int maxSeats;
	private int seats;

	public TravelClass() {

	}

	public TravelClass(String name, int maxSeats, int seats) {
		this.name = name;
		this.maxSeats = maxSeats;
		this.seats = seats;
	}


	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getMaxSeats() {
		return maxSeats;
	}

	public void setMaxSeats(int maxStoelen) {
		this.maxSeats = maxStoelen;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int stoelen) {
		this.seats = stoelen;
	}
}