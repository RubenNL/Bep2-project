package nl.hu.bep2.vliegmaatschappij.data;

import nl.hu.bep2.vliegmaatschappij.domein.TravelClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringTravelClassRepository extends JpaRepository<TravelClass, Integer> {
	Optional<TravelClass> findById(Integer id);
}
