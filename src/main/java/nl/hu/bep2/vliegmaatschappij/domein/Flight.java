package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.*;
import nl.hu.bep2.vliegmaatschappij.exceptions.DateException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Flight {
	@Id
	@GeneratedValue
	private int id;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	@ManyToOne
	private FlightRoute route;
	@ManyToMany
	private List<Booking> bookingList;
	@PrePersist
	private void checkDates() {
		if(departureTime.isAfter(arrivalTime)) throw new DateException("Invalid dates!");
	}
	@ManyToOne
	private Plane plane;
}
