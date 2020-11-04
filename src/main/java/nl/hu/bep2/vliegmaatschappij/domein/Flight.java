package nl.hu.bep2.vliegmaatschappij.domein;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import nl.hu.bep2.vliegmaatschappij.exceptions.DateException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Flight {
	@Id
	@GeneratedValue
	private int id;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JsonIgnore
	private FlightRoute route;
	private boolean canceled = false;

	@PrePersist
	private void checkDatesAndMakeTCFlist() {
		if(departureTime.isAfter(arrivalTime)) throw new DateException("Invalid dates!");
		Planetype pt = plane.getType();
		List<TravelClass> travelClassList = pt.getTravelclasses();
		for (TravelClass travelclass : travelClassList){
			this.travelClassFlightList.add(new TravelClassFlight(this, travelclass));
		}
	}

	@OneToMany(mappedBy="flight", cascade = CascadeType.ALL)
	private List<TravelClassFlight> travelClassFlightList=new ArrayList<>();
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JsonIgnore
	private Plane plane;


	public Flight(LocalDateTime departureTime, LocalDateTime arrivalTime, FlightRoute route, List<TravelClassFlight> travelClassFlightList, Plane plane) {
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.route = route;
		this.travelClassFlightList = travelClassFlightList;
		this.plane = plane;
		this.canceled = false;
	}

	public int getAvailableSeats() {
		int count=0;
		for(TravelClassFlight travelClassFlight:travelClassFlightList) count+= travelClassFlight.getAvailableSeats();
		return count;
	}

	public void cancel(){
		this.canceled = true;
	}

}
