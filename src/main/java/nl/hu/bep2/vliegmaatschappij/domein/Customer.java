package nl.hu.bep2.vliegmaatschappij.domein;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer extends Person {
    @Id
    @GeneratedValue
    private int id;
    @JsonIgnore
    @OneToMany(mappedBy="customer")
    List<Booking> bookings = new ArrayList<>();

	public Customer(String firstName, String lastName, LocalDate birthday, String email, String password, String phone, String nationality) {
		super(firstName, lastName, birthday, email, password, phone, nationality);
	}

	public Customer(String firstName, String lastName, LocalDate birthday, String email, String nationality, String phone) {
		super(firstName, lastName, birthday, email, phone, nationality);
	}

	public void addBooking(Booking booking){
		bookings.add(booking);
	}

	public void removeBooking(Booking booking){
		bookings.remove(booking);
	}

	public List<String> getBookingIDs(){
		List<String> bookingIDs = new ArrayList<>();
		for(Booking booking : bookings){
			bookingIDs.add(booking.getId());
		}
		return bookingIDs;
	}
}
