package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Planetype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany
    private List<TravelClass> travelclasses;
    @OneToMany
    private List<Plane> planes;

}
