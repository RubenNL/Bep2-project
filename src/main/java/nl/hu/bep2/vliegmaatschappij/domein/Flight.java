package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
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
}
