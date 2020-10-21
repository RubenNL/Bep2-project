package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@Setter
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
}
