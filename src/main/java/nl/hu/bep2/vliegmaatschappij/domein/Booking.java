package nl.hu.bep2.vliegmaatschappij.domein;


import nl.hu.bep2.vliegmaatschappij.security.data.User;

import javax.persistence.*;
import java.util.List;

public class Booking {
	@Id
	@GeneratedValue
	private int id;
	@ManyToMany
	private List<Person> persons;
	@Transient // TODO Uitzoeken hoe het zit met users/customers.
	private User user;
	@OneToOne
	private Price price;

	public Booking() {
	}

	public Booking(List<Person> persons, Price price) {
		this.persons = persons;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}
}
