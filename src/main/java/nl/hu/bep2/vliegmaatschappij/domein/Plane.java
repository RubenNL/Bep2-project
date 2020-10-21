package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Plane {
	@Id
	private String code;
	@ManyToOne
	private Planetype type;
}
