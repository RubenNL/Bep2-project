package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Klant extends Persoon {
    @Id
    @GeneratedValue
    private int id;
//    @OneToMany
	@Transient
    List<Boeking> boekingen = new ArrayList<>();

	public Klant(String voornaam, String achernaam, int leeftijd, String email, int telefoon, String nationaliteit) {
		super(voornaam, achernaam, leeftijd, email, telefoon, nationaliteit);
	}

	public List<Boeking> getBoekingen() {
		return boekingen;
	}

	public void addBoeking(Boeking boeking){
		boekingen.add(boeking);
	}

	public void removeBoeking(Boeking boeking){
		boekingen.remove(boeking);
	}
}
