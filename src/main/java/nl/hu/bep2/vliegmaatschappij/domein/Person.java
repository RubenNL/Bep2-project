package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Person {
	@Id
	@GeneratedValue
	protected int id;
	protected String firstName;
	protected String lastName;
	protected LocalDate birthday;
	protected String email;
	protected String phone;
	protected String nationality;
	@ManyToMany(mappedBy="persons",cascade= CascadeType.ALL)
	protected List<Booking> bookingList = new ArrayList<>();

	public Person(String firstName, String lastName, LocalDate birthday, String email, String phone, String nationality) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.email = email;
		this.phone = phone;
		this.nationality = nationality;
	}

	public Person(String firstName, String lastName, LocalDate birthday, String email, String phone, String nationality, List<Booking> bookingList) {
	}
}
