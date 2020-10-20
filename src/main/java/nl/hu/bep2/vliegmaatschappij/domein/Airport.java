package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Airport {
	@Id
	private String code;
	private String name;
	private double lat;
	private double lng;
	private String place;
	private String country;

	@OneToMany
	private List<FlightRoute> flightRoutes;

	public Airport(String code, String name, double lat, double lng, String place, String country, List<FlightRoute> flightRoutes) {
		this.code = code;
		this.name = name;
		this.lat = lat;
		this.lng = lng;
		this.place = place;
		this.country = country;
		this.flightRoutes = flightRoutes;
	}

	public Airport() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<FlightRoute> getFlightRoutes() {
		return flightRoutes;
	}

	public void setFlightRoutes(List<FlightRoute> flightRoutes) {
		this.flightRoutes = flightRoutes;
	}
}
