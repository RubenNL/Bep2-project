package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.*;
import java.util.List;

@Entity
public class Planetype {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String name;
	@Transient
	private List<TravelClass> travelclasses;

	@OneToMany
	private List<Plane> planes;

	public Planetype() {
	}

	public Planetype(String naam) {
		this.name = naam;
	}

}
