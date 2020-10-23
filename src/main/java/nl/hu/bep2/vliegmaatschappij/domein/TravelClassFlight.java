package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TravelClassFlight {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	private Flight flight;
	@OneToMany
	private List<Booking> bookingList;
	@ManyToOne
	private TravelClass travelClass;
	public int getAvailableSeats() {
		int count=travelClass.getMaxSeats();
		for(Booking booking:bookingList) count-=booking.getPersons().size();
		return count;
	}
}
