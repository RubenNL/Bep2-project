package nl.hu.bep2.vliegmaatschappij.data;

import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringFlightRepository extends JpaRepository<Flight, Integer> {
}
