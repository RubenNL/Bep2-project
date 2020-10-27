package nl.hu.bep2.vliegmaatschappij.application;

import nl.hu.bep2.vliegmaatschappij.data.SpringFlightRepository;
import nl.hu.bep2.vliegmaatschappij.domein.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class FindFlightService {
	private final SpringFlightRepository flightRepository;

	public FindFlightService(SpringFlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}
	//TODO filteren op klasse.
	public List<Flight> FindFlights(String departCode, String arivalCode, LocalDate departDate){
		return flightRepository.findByFlight(departCode, arivalCode, departDate.atStartOfDay(),departDate.atTime(23,59));
	}

	public List<Flight> FindAllAvailableFlights(){ //todo TESTEN!!!
		LocalDateTime dateTime = LocalDateTime.now();
		List<Flight> flightlistall = flightRepository.findByTime(dateTime);
		List<Flight> flightlistAvailable = new ArrayList<>();
		for (Flight flight : flightlistall){
			if(flight.getAvailableSeats() > 0){
				flightlistAvailable.add(flight);
			}
		}
		return flightlistAvailable;

	}
}
