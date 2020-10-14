package nl.hu.bep2.vliegmaatschappij.presentation.dto;

import nl.hu.bep2.vliegmaatschappij.domein.Flight;

import java.time.LocalDate;

public class FlightDTO {
    public int id;
    public LocalDate arrivalTime;

    public FlightDTO(Flight flight){
        this.id = flight.getId();
        this.arrivalTime = flight.getarrivalTime();
    }
}
