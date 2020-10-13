package nl.hu.bep2.vliegmaatschappij.domein;

import javax.annotation.processing.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class Vlucht {

	@Id
	@GeneratedValue
	private int id;
	private LocalDate vertrektijd;
	private LocalDate aankomsttijd;

	@Transient
	private List<Boeking> boekingList;

	public Vlucht(int id, LocalDate vertrektijd, LocalDate aankomsttijd) {
		this.id = id;
		this.vertrektijd = vertrektijd;
		this.aankomsttijd = aankomsttijd;
	}

	public Vlucht() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getVertrektijd() {
		return vertrektijd;
	}

	public void setVertrektijd(LocalDate vertrektijd) {
		this.vertrektijd = vertrektijd;
	}

	public LocalDate getAankomsttijd() {
		return aankomsttijd;
	}

	public void setAankomsttijd(LocalDate aankomsttijd) {
		this.aankomsttijd = aankomsttijd;
	}

	public List<Boeking> getBoekingList() {
		return boekingList;
	}

	public void setBoekingList(List<Boeking> boekingList) {
		this.boekingList = boekingList;
	}
}
