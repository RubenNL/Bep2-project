package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FlightRoute {
	@Id
	@GeneratedValue
	private int id;
	@OneToOne
	Airport departure;
	@OneToOne
	Airport destination;
}
