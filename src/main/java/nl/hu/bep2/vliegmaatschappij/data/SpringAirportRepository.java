package nl.hu.bep2.vliegmaatschappij.data;

import nl.hu.bep2.vliegmaatschappij.domein.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringAirportRepository extends JpaRepository<Airport, String> {
}