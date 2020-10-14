package nl.hu.bep2.vliegmaatschappij.domein;


import nl.hu.bep2.vliegmaatschappij.security.data.User;

import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.List;

public class Booking {
	@ManyToMany
	private List<Person> persons;
	@Transient // TODO Uitzoeken hoe het zit met users/customers.
	private User user;
	@OneToOne
	private Price price;

	public Booking() {
	}
}
