package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
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
}
