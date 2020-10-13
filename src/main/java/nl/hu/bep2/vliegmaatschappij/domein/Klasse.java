package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;

@Entity
public class Klasse {
    private String naam;
    private int maxStoelen;
    private int stoelen;

    public Klasse(String naam, int maxStoelen, int stoelen) {
        this.naam = naam;
        this.maxStoelen = maxStoelen;
        this.stoelen = stoelen;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getMaxStoelen() {
        return maxStoelen;
    }

    public void setMaxStoelen(int maxStoelen) {
        this.maxStoelen = maxStoelen;
    }

    public int getStoelen() {
        return stoelen;
    }

    public void setStoelen(int stoelen) {
        this.stoelen = stoelen;
    }
}