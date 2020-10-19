package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Flight {

	@Id
	@GeneratedValue
	private int id;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;

	@Transient
	private List<Booking> bookingList;

	public Flight(LocalDateTime departureTime, LocalDateTime arrivalTime) {
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

	public LocalDateTime getdepartureTime() {
		return departureTime;
	}

	public void setdepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalDateTime getarrivalTime() {
		return arrivalTime;
	}

	public void setarrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public List<Booking> getBoekingList() {
		return bookingList;
	}

	public void setBoekingList(List<Booking> bookingList) {
		this.bookingList = bookingList;
	}
}
