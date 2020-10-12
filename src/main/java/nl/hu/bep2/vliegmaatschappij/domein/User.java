package nl.hu.bep2.vliegmaatschappij.domein;

public class User extends Persoon {
    public Role role;

    public User(String voornaam, String achernaam, int leeftijd, String email, int telefoon, String nationaliteit, Role role) {
        super(voornaam, achernaam, leeftijd, email, telefoon, nationaliteit);
        this.role = role;
    }
}
