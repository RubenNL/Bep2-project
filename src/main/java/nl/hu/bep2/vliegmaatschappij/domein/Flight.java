package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Flight {

	@Id
	@GeneratedValue
	private int id;
	private LocalDate departureTime;
	private LocalDate arrivalTime;

	@Transient
	private List<Booking> bookingList;

	public Flight(int id, LocalDate departureTime, LocalDate arrivalTime) {
		this.id = id;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}

	public Flight() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getdepartureTime() {
		return departureTime;
	}

	public void setdepartureTime(LocalDate departureTime) {
		this.departureTime = departureTime;
	}

	public LocalDate getarrivalTime() {
		return arrivalTime;
	}

	public void setarrivalTime(LocalDate arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public List<Booking> getBoekingList() {
		return bookingList;
	}

	public void setBoekingList(List<Booking> bookingList) {
		this.bookingList = bookingList;
	}
}
