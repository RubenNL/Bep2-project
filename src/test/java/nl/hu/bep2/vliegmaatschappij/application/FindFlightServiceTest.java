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
import org.springframework.util.RouteMatcher;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

//	@Test
//	void testFindFlight() {
//		//Test if self-made flight is identical to flight 10 in database by finding with FindAvailableFlights
//		String str = "2020-10-09 10:02:00";
//		String str1 = "2020-10-09 10:54:24";
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
//		LocalDateTime dateTime2 = LocalDateTime.parse(str1, formatter);
//
//		Airport depart = airportRepository.getOne("HKG");
//		Airport destination = airportRepository.getOne("YOW");
//		Plane plane = planeRepository.getOne("PH-BGI");
//		FlightRoute route = new FlightRoute();
//		Flight flight = new Flight();
//		flight.setDepartureTime(dateTime2);
//		flight.setArrivalTime(dateTime);
//		flight.setId(10);
//		flight.setRoute(route);
//		flight.setPlane(plane);
//		flight.setTravelClassFlightList(new ArrayList<>());
//
//		Flight foundFlight = service.FindAvailableFlights("HKG", "YOW", dateTime2.toLocalDate(), plane.getType().getTravelclasses().get(0).getId()).get(0);
//		foundFlight.setTravelClassFlightList(new ArrayList<>());
//		foundFlight.setRoute(route);
//		assertEquals(flight, foundFlight);
//
//	}

	@Test
	void testFindFlight() {
		String str = "2016-03-04 11:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

		Plane plane = planeRepository.getOne("PH-BVG");
		Airport dest = airportRepository.getOne("AMS");
		Airport depart = airportRepository.getOne("BRU");
		FlightRoute route = new FlightRoute();
		route.setDestination(depart);
		route.setDeparture(dest);

		Flight flight2 = new Flight(dateTime, dateTime.plusHours(1), route, new ArrayList<>() ,plane);
		repository.save(flight2);

		assertEquals(Collections.singletonList(flight2), service.FindAvailableFlights("AMS", "BRU", dateTime.toLocalDate(),plane.getType().getTravelclasses().get(0).getId()));
	}

	@Test
	void testFindAllFlight(){
		//Tested for 28/10/2021
		List<Flight> flights = service.FindAllAvailableFlights();
		assertEquals(7, flights.size());
	}

}