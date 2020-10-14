package nl.hu.bep2.vliegmaatschappij.presentation.dto;

import nl.hu.bep2.vliegmaatschappij.domein.Flight;

public class FlightDTO {
    public int id;
    public FlightDTO(Flight flight){
        this.id = flight.getId();
    }
}
