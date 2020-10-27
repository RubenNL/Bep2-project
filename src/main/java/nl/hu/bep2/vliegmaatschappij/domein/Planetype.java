package nl.hu.bep2.vliegmaatschappij.domein;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Planetype {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @OneToMany(mappedBy="planeType")
    private List<TravelClass> travelclasses;
    @OneToMany(mappedBy="type")
    @JsonIgnore //todo Geeft conflicten met plane/all
    private List<Plane> planes;

    public Planetype(String name) {
        this.name = name;
    }
}
