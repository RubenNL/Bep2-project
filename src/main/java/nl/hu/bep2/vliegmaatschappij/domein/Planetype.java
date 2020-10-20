package nl.hu.bep2.vliegmaatschappij.domein;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
public class Planetype {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String name;
	@OneToMany
	private List<TravelClass> travelclasses;
	@OneToMany
	private List<Plane> planes;

	public Planetype() {
	}

	public Planetype(String naam) {
		this.name = naam;
	}

}
