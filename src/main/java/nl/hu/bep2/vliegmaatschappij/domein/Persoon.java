package nl.hu.bep2.vliegmaatschappij.domein;

public class Persoon {
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
}
