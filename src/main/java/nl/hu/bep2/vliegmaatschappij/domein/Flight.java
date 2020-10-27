package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.*;
import nl.hu.bep2.vliegmaatschappij.exceptions.DateException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
	//todo Constraint arrival mag niet voor departure zijn!!
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	@ManyToOne(cascade = CascadeType.ALL)
	private FlightRoute route;
	@ManyToMany
	private List<Booking> bookingList;
	@PrePersist
	private void checkDates() {
		if(departureTime.isAfter(arrivalTime)) throw new DateException("Invalid dates!");
	}
	@OneToMany(mappedBy="flight", cascade = CascadeType.ALL)
	private List<TravelClassFlight> travelClassFlightList=new ArrayList<>();
	@ManyToOne(cascade = CascadeType.ALL)
	private Plane plane;

	public Flight(LocalDateTime departureTime, LocalDateTime arrivalTime, FlightRoute route, List<TravelClassFlight> travelClassFlightList, Plane plane) {
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.route = route;
		this.travelClassFlightList = travelClassFlightList;
		this.plane = plane;
	}

	public int getAvailableSeats() {
		int count=0;
		for(TravelClassFlight travelClassFlight:travelClassFlightList) count+= travelClassFlight.getAvailableSeats();
		return count;
	}
}
