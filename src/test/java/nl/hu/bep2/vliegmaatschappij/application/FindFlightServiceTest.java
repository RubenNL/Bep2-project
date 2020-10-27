package nl.hu.bep2.vliegmaatschappij.application;

import nl.hu.bep2.vliegmaatschappij.data.SpringAirportRepository;
import nl.hu.bep2.vliegmaatschappij.data.SpringFlightRepository;
import nl.hu.bep2.vliegmaatschappij.data.SpringPlaneRepository;
import nl.hu.bep2.vliegmaatschappij.domein.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class FindFlightServiceTest {
	@Autowired
	private SpringFlightRepository repository;
	@Autowired
	private CustomerFlightService service;
	@Autowired
	private SpringAirportRepository airportRepository;
	@Autowired
	private SpringPlaneRepository planeRepository;
	@Test
	void testFindRoute() {
		String str = "2016-03-04 11:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		Airport dest = airportRepository.getOne("AMS");
		Airport depart = airportRepository.getOne("BRU");
		Plane plane = planeRepository.getOne("PH-BVC");
		FlightRoute route = new FlightRoute();
		route.setDestination(dest);
		route.setDeparture(depart);
		FlightRoute route2 = new FlightRoute();
		Flight flight1 = new Flight(dateTime, dateTime.plusHours(1), route, new ArrayList<>() ,plane);
		flight1 = repository.getOne(repository.save(flight1).getId());
		//plane=flight1.getPlane();
		route2.setDestination(depart);
		route2.setDeparture(dest);
		plane = planeRepository.getOne("PH-BVG");
		Flight flight2 = new Flight(dateTime, dateTime.plusHours(1), route2, new ArrayList<>() ,plane);
		repository.save(flight2);

		Flight flight3 = new Flight(dateTime.minusDays(1), dateTime.minusDays(1).plusHours(1), route2, new ArrayList<>() ,plane);
		repository.save(flight3);
		assertEquals(Collections.singletonList(flight2), service.FindAvailableFlights("AMS", "BRU", dateTime.toLocalDate(),plane.getType().getTravelclasses().get(0).getId()));

	}
}