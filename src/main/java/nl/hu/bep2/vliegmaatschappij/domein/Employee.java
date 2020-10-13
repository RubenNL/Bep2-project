package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Employee extends Person {
	@Id
	@GeneratedValue
	private int id;

	public Employee(String firstName, String lastName, int age, String email, int phone, String nationality) {
		super(firstName, lastName, age, email, phone, nationality);
	}

}
