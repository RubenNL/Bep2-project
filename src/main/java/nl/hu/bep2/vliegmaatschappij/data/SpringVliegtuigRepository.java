package nl.hu.bep2.vliegmaatschappij.data;

import nl.hu.bep2.vliegmaatschappij.domein.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringVliegtuigRepository extends JpaRepository<Plane, Plane> {
	@Override
	Optional<Plane> findById(Plane plane);
}
