package nl.hu.bep2.vliegmaatschappij.domein;

import javax.persistence.*;
import java.util.List;

@Entity
public class User extends Persoon {
    @Id
    @GeneratedValue
    private int id;
    @Enumerated(EnumType.STRING)
    private Role role;
    // TODO: List relatie met Boeking

    public User(String voornaam, String achernaam, int leeftijd, String email, int telefoon, String nationaliteit, Role role) {
        super(voornaam, achernaam, leeftijd, email, telefoon, nationaliteit);
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
