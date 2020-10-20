package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.*;
import java.util.List;

@Entity
public class Booking {
	@Id
	@GeneratedValue
	private int id;
	@ManyToMany
	private List<Person> persons;
	@OneToOne
	private Customer customer;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}
}
