package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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
    @OneToMany(mappedBy="customer")
    List<Booking> bookings = new ArrayList<>();
	
	public void addBooking(Booking booking){
		bookings.add(booking);
	}

	public void removeBooking(Booking booking){
		bookings.remove(booking);
	}
}
