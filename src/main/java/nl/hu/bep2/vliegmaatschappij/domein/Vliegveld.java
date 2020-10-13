package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Vliegveld {
	@Id
	private String code;
	private String naam;
	private double lat;
	private double lng;
	private String stad;
	private String land;

	@Transient
	private Vluchtroute vluchtRoute;

	public Vliegveld(String code, String naam, double lat, double lng, String stad, String land, Vluchtroute vluchtRoute) {
		this.code = code;
		this.naam = naam;
		this.lat = lat;
		this.lng = lng;
		this.stad = stad;
		this.land = land;
		this.vluchtRoute = vluchtRoute;
	}

	public Vliegveld() {
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
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

	public String getStad() {
		return stad;
	}

	public void setStad(String stad) {
		this.stad = stad;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}
}
