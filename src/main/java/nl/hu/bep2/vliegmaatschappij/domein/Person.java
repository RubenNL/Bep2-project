package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person {
	@Id
	@GeneratedValue
	protected int id;
	protected String firstName;
	protected String lastName;
	protected int age;
	protected String email;
	protected int phone;
	protected String nationality;

	public Person() {

	}

	public Person(String firstName, String lastName, int age, String email, int phone, String nationality) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.nationality = nationality;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String voornaam) {
		this.firstName = voornaam;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String achernaam) {
		this.lastName = achernaam;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int leeftijd) {
		this.age = leeftijd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int telefoon) {
		this.phone = telefoon;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationaliteit) {
		this.nationality = nationaliteit;
	}
}
