package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;

@Entity
public class Prijs {
    private double prijs;

    public Prijs(Vliegtuigtype type, Klasse klasse, Vluchtroute route) {
        this.prijs = berekenPrijs(type,klasse,route);
    }

    public double berekenPrijs(Vliegtuigtype type, Klasse klasse, Vluchtroute route){
        // Hier een calculatie maken voor de prijs
        return 0.0;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }
}
