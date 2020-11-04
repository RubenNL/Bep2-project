package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Employee extends Person {
	@Id
	@GeneratedValue
	private int id;

	public Employee(String firstName, String lastName, LocalDate birthday, String email, String password, String phone, String nationality) {
		super(firstName, lastName, birthday, email, password, phone, nationality);
	}
}
