package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FlightRoute {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	private Airport departure;
	@ManyToOne
	private Airport destination;
}
