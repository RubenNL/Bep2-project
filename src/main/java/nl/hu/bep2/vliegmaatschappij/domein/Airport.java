package nl.hu.bep2.vliegmaatschappij.domein;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Airport {
	@Id
	private String code;
	private String name;
	private double lat;
	private double lng;
	private String place;
	private String country;

	@OneToMany(mappedBy="departure")
	@JsonIgnore
	private List<FlightRoute> departures;
	@OneToMany(mappedBy="destination")
	@JsonIgnore
	private List<FlightRoute> arrivals;
}
