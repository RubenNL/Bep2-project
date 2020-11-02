package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee extends Person {
	@Id
	@GeneratedValue
	private int id;

	public Employee(String firstName, String lastName, LocalDate birthday, String email, String phone, String nationality) {
		super(firstName, lastName, birthday, email, phone, nationality);
	}
}
