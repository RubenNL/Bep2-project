package nl.hu.bep2.vliegmaatschappij.data;

import nl.hu.bep2.vliegmaatschappij.domein.Planetype;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringPlanetypeRepository extends JpaRepository<Planetype, Integer> {
}
