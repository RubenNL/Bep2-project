package nl.hu.bep2.vliegmaatschappij.domein;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TravelClassFlight {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	@JsonIgnore
	private Flight flight;

	@OneToMany(mappedBy="travelClassFlight")
	private List<Booking> bookingList = new ArrayList<>();

	@ManyToOne
	private TravelClass travelClass;

	public TravelClassFlight(Flight flight, TravelClass travelClass) {
		this.flight = flight;
		this.travelClass = travelClass;
	}

	public int getAvailableSeats() {
		int count=travelClass.getMaxSeats();
		for(Booking booking:bookingList) count-=booking.getPersons().size();
		return count;
	}

	public double calculatePrice(){
		return travelClass.getPricePerKm() * flight.getRoute().getDistance();
	}
}
