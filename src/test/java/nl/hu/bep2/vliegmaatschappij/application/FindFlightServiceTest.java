package nl.hu.bep2.vliegmaatschappij.application;

import nl.hu.bep2.vliegmaatschappij.data.SpringFlightRepository;
import nl.hu.bep2.vliegmaatschappij.domein.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class FindFlightServiceTest {
	@Autowired
	private SpringFlightRepository repository;
	@Autowired
	private FindFlightService service;

	@Test
	void testFindRoute() {
		String str = "2016-03-04 11:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		Airport dest = new Airport("aaa", "aaa", 52.304775, 4.758313, "Netherlands", "Schiphol", new ArrayList<>(), new ArrayList<>());
		Airport depart = new Airport("bbb", "bbb", 51.958973, 4.445763, "Netherlands", "Rotterdam", new ArrayList<>(), new ArrayList<>());
		Planetype type = new Planetype("Boing747");
		Plane plane = new Plane("0101", type);
		FlightRoute route = new FlightRoute();
		route.setDestination(dest);
		route.setDeparture(depart);
		FlightRoute route2 = new FlightRoute();
		Flight flight1 = new Flight(dateTime, LocalDateTime.now(), route, new ArrayList<TravelClassFlight>() ,plane);

		route2.setDestination(depart);
		route2.setDeparture(dest);
		Flight flight2 = new Flight(dateTime, LocalDateTime.now(), route2, new ArrayList<TravelClassFlight>() ,plane);
		repository.save(flight1);
		repository.save(flight2);

		assertEquals(Arrays.asList(flight1), service.FindFlights("aaa", "bbb", dateTime.toLocalDate()));
	}
}