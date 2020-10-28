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
public class CustomerFlightService {
	private final SpringFlightRepository flightRepository;

	public CustomerFlightService(SpringFlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}
	//TODO TESTEN!!!
	//TODO Canceled flights niet laten zien
	public List<Flight> FindAvailableFlights(String departCode, String arivalCode, LocalDate departDate, int classID){
		List<Flight> flightlistall = flightRepository.findByFlight(departCode, arivalCode, departDate.atStartOfDay(),departDate.atTime(23,59));
		List<Flight> flightlistAvailable = new ArrayList<>();
		for (Flight flight : flightlistall){
			for(TravelClass tc : flight.getPlane().getType().getTravelclasses()){
				if(tc.getId() == classID){
					flightlistAvailable.add(flight);
				}
			}
		}
		return flightlistAvailable;

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
