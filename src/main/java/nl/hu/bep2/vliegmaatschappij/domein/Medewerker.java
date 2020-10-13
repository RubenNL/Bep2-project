package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Medewerker extends Persoon {
	@Id
	@GeneratedValue
	private int id;

	public Medewerker(String voornaam, String achernaam, int leeftijd, String email, int telefoon, String nationaliteit) {
		super(voornaam, achernaam, leeftijd, email, telefoon, nationaliteit);
	}

}
