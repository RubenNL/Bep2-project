package nl.hu.bep2.vliegmaatschappij.domein;

import nl.hu.bep2.vliegmaatschappij.data.SpringAirportRepository;
import nl.hu.bep2.vliegmaatschappij.data.SpringFlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class FlightRouteTest {
	@Autowired
	private SpringAirportRepository repository;
	@Test
	void distanceTest() {
		Airport dest = repository.getOne("AMS");
		Airport depart = repository.getOne("BRU");
		FlightRoute route=new FlightRoute();
		route.setDestination(dest);
		route.setDeparture(depart);
		assertEquals(157,route.getDistance());
	}
	@Test
	void longDistanceTest() {
		Airport dest = repository.getOne("AMS");
		Airport depart = repository.getOne("MEL");
		FlightRoute route=new FlightRoute();
		route.setDestination(dest);
		route.setDeparture(depart);
		assertEquals(16538,route.getDistance());
	}
}