package nl.hu.bep2.vliegmaatschappij.data;

import nl.hu.bep2.vliegmaatschappij.domein.Vliegtuig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringVliegtuigRepository extends JpaRepository<Vliegtuig, Vliegtuig> {
	@Override
	Optional<Vliegtuig> findById(Vliegtuig vliegtuig);
}
