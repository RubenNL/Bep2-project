package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Plane {
	@Id
	private String code;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Planetype type;
	@OneToMany(mappedBy="plane")
	@ToString.Exclude
	private List<Flight> flights=new ArrayList<>();

	public Plane(String code, Planetype type) {
		this.code = code;
		this.type = type;
	}
}
