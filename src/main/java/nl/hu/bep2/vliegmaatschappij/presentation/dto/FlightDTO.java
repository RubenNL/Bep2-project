package nl.hu.bep2.vliegmaatschappij.presentation.dto;

import nl.hu.bep2.vliegmaatschappij.domein.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FlightDTO {
	public int id;
	public LocalDateTime arrivalTime;
	public LocalDateTime departureTime;
	public FlightDTO() {}
	public FlightDTO(Flight flight){
		this.id = flight.getId();
		this.arrivalTime = flight.getarrivalTime();
		this.departureTime = flight.getdepartureTime();
	}
}
