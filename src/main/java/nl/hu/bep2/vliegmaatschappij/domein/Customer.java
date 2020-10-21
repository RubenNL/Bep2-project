package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Customer extends Person {
    @Id
    @GeneratedValue
    private int id;
    @OneToMany
    List<Booking> bookings = new ArrayList<>();
	
	public void addBooking(Booking booking){
		bookings.add(booking);
	}

	public void removeBooking(Booking booking){
		bookings.remove(booking);
	}
}
