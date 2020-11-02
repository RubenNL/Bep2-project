package nl.hu.bep2.vliegmaatschappij.domein;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import nl.hu.bep2.vliegmaatschappij.presentation.DTO.TravelClassDTO;

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
    @OneToMany(mappedBy="planeType", cascade = CascadeType.PERSIST)

    @ToString.Exclude
    private List<TravelClass> travelclasses;
    @OneToMany(mappedBy="type")

    @JsonIgnore
    @ToString.Exclude
    private List<Plane> planes;

    public Planetype(String name) {
        this.name = name;
    }

}
