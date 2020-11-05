package nl.hu.bep2.vliegmaatschappij.data;

import nl.hu.bep2.vliegmaatschappij.domein.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringPlaneRepository extends JpaRepository<Plane, String> {
}
