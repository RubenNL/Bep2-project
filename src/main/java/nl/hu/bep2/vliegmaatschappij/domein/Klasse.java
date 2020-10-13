package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Klasse {
    @Id
    @GeneratedValue
    private int id;
    private String naam;
    private int maxStoelen;
    private int stoelen;

    public Klasse(String naam, int maxStoelen, int stoelen) {
        this.naam = naam;
        this.maxStoelen = maxStoelen;
        this.stoelen = stoelen;
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
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