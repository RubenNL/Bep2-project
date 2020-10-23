package nl.hu.bep2.vliegmaatschappij.domein;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FlightRouteTest {
	@Test
	void distanceTest() {
		Airport dest = new Airport("aaa", "aaa", 52.304775, 4.758313, "Netherlands", "Schiphol",new ArrayList<>(),new ArrayList<>());
		Airport depart = new Airport("bbb", "bbb", 51.958973, 4.445763, "Netherlands", "Rotterdam",new ArrayList<>(),new ArrayList<>());
		FlightRoute route=new FlightRoute();
		route.setDestination(dest);
		route.setDeparture(depart);
		assertEquals(route.getDistance(),43);
	}
	@Test
	void longDistanceTest() {
		Airport dest = new Airport("aaa", "aaa", 52.304775, 4.758313, "Netherlands", "Schiphol",new ArrayList<>(),new ArrayList<>());
		Airport depart = new Airport("ccc", "ccc", -31.943630, 115.966773, "Australia", "Perth",new ArrayList<>(),new ArrayList<>());
		FlightRoute route=new FlightRoute();
		route.setDestination(dest);
		route.setDeparture(depart);
		assertEquals(route.getDistance(),14158);
	}
}