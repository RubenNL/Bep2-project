package nl.hu.bep2.vliegmaatschappij.presentation.dto;

import nl.hu.bep2.vliegmaatschappij.domein.Airport;

public class AirportDTO {
	public String code;
	public AirportDTO(Airport airport){
		this.code = airport.getCode();
	}
}
