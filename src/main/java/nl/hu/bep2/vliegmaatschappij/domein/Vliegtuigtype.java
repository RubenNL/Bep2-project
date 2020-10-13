package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.*;
import java.util.List;

@Entity
public class Vliegtuigtype {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String naam;
	@Transient
	private List<Klasse> klassen;

	@OneToMany
	private List<Vliegtuig> vliegtuigen;

	public Vliegtuigtype() {
	}

	public Vliegtuigtype(String naam) {
		this.naam = naam;
	}


}
