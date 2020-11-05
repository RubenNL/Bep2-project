package nl.hu.bep2.vliegmaatschappij.domein;

import nl.hu.bep2.vliegmaatschappij.data.SpringAirportRepository;
import nl.hu.bep2.vliegmaatschappij.data.SpringFlightRepository;
import nl.hu.bep2.vliegmaatschappij.data.SpringPlaneRepository;
import nl.hu.bep2.vliegmaatschappij.data.SpringTravelClassRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class TravelClassFlightTest {
	@Autowired
	private SpringPlaneRepository planeRepository;
	@Autowired
	private SpringAirportRepository airportRepository;
	@Autowired
	private SpringTravelClassRepository travelClassRepository;

	@Test
	void calculatePriceTest(){
		String str = "2016-03-04 11:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

		Plane plane = planeRepository.getOne("PH-BVG");
		Airport dest = airportRepository.getOne("AMS");
		Airport depart = airportRepository.getOne("BRU");
		FlightRoute route = new FlightRoute();
		route.setDestination(dest);
		route.setDeparture(depart);

		Flight flight = new Flight(dateTime, dateTime.plusHours(3), route, new ArrayList<>() ,plane);

		TravelClass travelClass = travelClassRepository.getOne(1);

		TravelClassFlight tCF = new TravelClassFlight(flight, travelClass);

		assertEquals(31.4, tCF.calculatePrice());
	}
}
