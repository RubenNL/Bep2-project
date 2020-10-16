package nl.hu.bep2.vliegmaatschappij.presentation.dto;

import nl.hu.bep2.vliegmaatschappij.domein.TravelClass;

public class TravelClassDTO {
    public int id;
    public String name;
    public int maxSeats;
    public int availableSeats;

	public TravelClassDTO() {
	}

	public TravelClassDTO(TravelClass travelClass) {
		this.id = travelClass.getId();
		this.name = travelClass.getName();
		this.maxSeats = travelClass.getMaxSeats();
		this.availableSeats = travelClass.getAvailableSeats();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getMaxSeats() {
		return maxSeats;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}
}
