package nl.hu.bep2.vliegmaatschappij.data;

import nl.hu.bep2.vliegmaatschappij.domein.TravelClassFlight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringTravelClassFlightRepository extends JpaRepository<TravelClassFlight, Integer> {

}
