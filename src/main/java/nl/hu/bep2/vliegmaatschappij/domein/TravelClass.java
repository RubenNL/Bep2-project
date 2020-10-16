package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class TravelClass {
	@Id
	@GeneratedValue
	private int id;
	@NotNull
	private String name;
	@NotNull
	@Positive
	private int maxSeats;
	@PositiveOrZero
	private int availableSeats;

	public TravelClass() {

	}

	public TravelClass(String name, int maxSeats, int availableSeats) {
		this.name = name;
		this.maxSeats = maxSeats;
		this.availableSeats = availableSeats;
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

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int stoelen) {
		this.availableSeats = stoelen;
	}

	@Override
	public String toString() {
		return "TravelClass{" +
				"id=" + id +
				", name='" + name + '\'' +
				", maxSeats=" + maxSeats +
				", availableSeats=" + availableSeats +
				'}';
	}
}