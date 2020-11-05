package nl.hu.bep2.vliegmaatschappij.domein;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Customer extends Person {
    @Id
    @GeneratedValue
    private int id;
    @JsonIgnore
    @OneToMany(mappedBy="customer",cascade=CascadeType.REMOVE)
    List<Booking> bookings = new ArrayList<>();

	public Customer(String firstName, String lastName, LocalDate birthday, String email, String password, String phone, String nationality) {
		super(firstName, lastName, birthday, email, password, phone, nationality);
	}

	public Customer(String firstName, String lastName, LocalDate birthday, String email, String nationality, String phone) {
		super(firstName, lastName, birthday, email, phone, nationality);
	}

	public List<String> getBookingIDs(){
		List<String> bookingIDs = new ArrayList<>();
		for(Booking booking : bookings){
			bookingIDs.add(booking.getId());
		}
		return bookingIDs;
	}
}
