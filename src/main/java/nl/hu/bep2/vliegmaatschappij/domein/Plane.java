package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Plane {
	@Id
	private String code;
	@ManyToOne
	private Planetype type;

	public Plane(){}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Planetype getType() {
		return type;
	}

	public void setType(Planetype type) {
		this.type = type;
	}
}
