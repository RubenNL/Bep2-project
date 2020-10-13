package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Vliegtuig {
	@Id
	private String code;
	@ManyToOne
	private Vliegtuigtype type;

	public Vliegtuig(){}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Vliegtuigtype getType() {
		return type;
	}

	public void setType(Vliegtuigtype type) {
		this.type = type;
	}
}
