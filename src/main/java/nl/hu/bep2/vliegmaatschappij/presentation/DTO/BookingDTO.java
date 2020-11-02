package nl.hu.bep2.vliegmaatschappij.presentation.DTO;

import nl.hu.bep2.vliegmaatschappij.domein.Person;
import java.util.List;

public class BookingDTO {
	public int FlightID;
	public int travelClassID;
	public List<PersonDTO> personDTOS;
}
