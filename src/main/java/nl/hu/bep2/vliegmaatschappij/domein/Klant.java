package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Klant extends Persoon {
    @Id
    @GeneratedValue
    private int id;
    private Role role;

    public Klant(String voornaam, String achernaam, int leeftijd, String email, int telefoon, String nationaliteit, Role role) {
        super(voornaam, achernaam, leeftijd, email, telefoon, nationaliteit);
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
