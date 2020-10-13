package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Persoon {
	@Id
	@GeneratedValue
	protected int id;
	protected String voornaam;
	protected String achernaam;
	protected int leeftijd;
	protected String email;
	protected int telefoon;
	protected String nationaliteit;

	public Persoon(String voornaam, String achernaam, int leeftijd, String email, int telefoon, String nationaliteit) {
		this.voornaam = voornaam;
		this.achernaam = achernaam;
		this.leeftijd = leeftijd;
		this.email = email;
		this.telefoon = telefoon;
		this.nationaliteit = nationaliteit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getAchernaam() {
		return achernaam;
	}

	public void setAchernaam(String achernaam) {
		this.achernaam = achernaam;
	}

	public int getLeeftijd() {
		return leeftijd;
	}

	public void setLeeftijd(int leeftijd) {
		this.leeftijd = leeftijd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTelefoon() {
		return telefoon;
	}

	public void setTelefoon(int telefoon) {
		this.telefoon = telefoon;
	}

	public String getNationaliteit() {
		return nationaliteit;
	}

	public void setNationaliteit(String nationaliteit) {
		this.nationaliteit = nationaliteit;
	}
}
