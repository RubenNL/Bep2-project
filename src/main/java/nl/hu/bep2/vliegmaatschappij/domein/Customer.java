package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends Person {
    @Id
    @GeneratedValue
    private int id;
    @OneToMany
    List<Booking> bookings = new ArrayList<>();
	
	public Customer() {
		super();
	}

	public Customer(String firstName, String lastName, int age, String email, int phone, String nationality) {
		super(firstName, lastName, age, email, phone, nationality);
	}

	public List<Booking> getBookings(){
		return bookings;
	}

	public void addBooking(Booking booking){
		bookings.add(booking);
	}

	public void removeBooking(Booking booking){
		bookings.remove(booking);
	}
}
