package nl.hu.bep2.vliegmaatschappij.data;

import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import nl.hu.bep2.vliegmaatschappij.domein.TravelClassFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SpringTravelClassFlightRepository extends JpaRepository<TravelClassFlight, Integer> {

}
