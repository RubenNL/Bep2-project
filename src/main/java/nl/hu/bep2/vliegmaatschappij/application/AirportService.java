package nl.hu.bep2.vliegmaatschappij.application;

import nl.hu.bep2.vliegmaatschappij.data.SpringAirportRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Airport;
import nl.hu.bep2.vliegmaatschappij.domein.FlightRoute;
import nl.hu.bep2.vliegmaatschappij.exceptions.AirportNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {
	private final SpringAirportRepository airportService;

	public AirportService(SpringAirportRepository airportService) {
		this.airportService = airportService;
	}


	public Airport showAirport(String airportCode) throws AirportNotFoundException {
		return airportService.findById(airportCode)
				.orElseThrow(() -> new AirportNotFoundException("Airport with the code: " + airportCode + " not found."));
	}

	public void createAirport(String code, String name, double lat, double lng, String place, String country, List<FlightRoute> flightRoutes) {

		Airport airport = new Airport(code,name,lat,lng,place,country, flightRoutes);

		this.airportService.save(airport);
	}

	public void delete(String code) {
		this.airportService.deleteById(code);
	}
}
