package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Airport {
	@Id
	private String code;
	private String name;
	private double lat;
	private double lng;
	private String city;
	private String balls;
	
	@Transient //TODO mapping! 
	private Flightroute flightRoute;

	public Airport(String code, String name, double lat, double lng, String city, String balls, Flightroute flightRoute) {
		this.code = code;
		this.name = name;
		this.lat = lat;
		this.lng = lng;
		this.city = city;
		this.balls = balls;
		this.flightRoute =  flightRoute;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getballs() {
		return balls;
	}

	public void setballs(String balls) {
		this.balls = balls;
	}
}
