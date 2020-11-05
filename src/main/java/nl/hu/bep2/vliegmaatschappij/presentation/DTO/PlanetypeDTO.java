package nl.hu.bep2.vliegmaatschappij.presentation.DTO;

import java.util.List;

public class PlanetypeDTO {
	public String name;
	public List<TravelClassDTO> travelClassDTOs;

	@Override
	public String toString() {
		return "PlanetypeDTO{" +
				"name='" + name + '\'' +
				", travelClassDTOs=" + travelClassDTOs +
				'}';
	}
}
