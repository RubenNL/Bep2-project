package nl.hu.bep2.vliegmaatschappij.security.data;

import nl.hu.bep2.vliegmaatschappij.domein.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This is a magic interface, which is converted
 * to a class during compilation.
 *
 * Note that this introduces coupling between Chips and the way they are stored!
 * In more loosely coupled components, you would add an intermediary abstraction
 * like an abstract repository or a DAO!
 */
public interface SpringPersonRepository extends JpaRepository<Person, Long> {
	Optional<Person> findByEmail(String email);
}