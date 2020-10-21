package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
@NoArgsConstructor
@Getter
@Setter
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