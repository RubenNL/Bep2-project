package nl.hu.bep2.vliegmaatschappij.data;

import nl.hu.bep2.vliegmaatschappij.domein.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringBookingRepository extends JpaRepository<Booking, Integer> {
}
