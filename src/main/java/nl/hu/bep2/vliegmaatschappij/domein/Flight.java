package nl.hu.bep2.vliegmaatschappij.domein;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
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
}
