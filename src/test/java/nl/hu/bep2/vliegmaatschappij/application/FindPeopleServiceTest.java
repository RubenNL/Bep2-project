package nl.hu.bep2.vliegmaatschappij.application;

import nl.hu.bep2.vliegmaatschappij.exceptions.DuplicateException;
import nl.hu.bep2.vliegmaatschappij.security.application.UserService;
import nl.hu.bep2.vliegmaatschappij.security.data.SpringPersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class FindPeopleServiceTest {
	@Autowired
	private SpringPersonRepository personRepository;
	@Autowired
	private UserService userService;

	@Test
	void findNonExistentAccountTest(){
		assertTrue(personRepository.findByEmail("UnavailableEmail@example.com").isEmpty());
	}

	@Test
	void findAccountTest(){
		assertTrue(personRepository.findByEmail("Employee1@example.com").isPresent());
	}

	@Test
	void deleteNonExistantAccountTest(){
		assertThrows(
				EmptyResultDataAccessException.class,
				() -> userService.delete(666));
	}

	@Test
	void createAccountAndDeleteTest(){
		userService.register("Lisa",
				"Smit", LocalDate.now().minusMonths(3),
				"Employee6@example.com", "5678",
				"0676462648", "Italiaans");

		assertTrue(personRepository.findByEmail("Employee6@example.com").isPresent());

		assertDoesNotThrow(
				() -> userService.delete(personRepository.findByEmail("Employee6@example.com").get().getId()),
				"Dit account zou moeten bestaan!");
	}

	@Test
	void createSameEmailTest(){

		userService.register("John",
				"Smit", LocalDate.now(),
				"example@example.com", "1234",
				"0612345678", "Amerikaans");

		assertThrows(
				DuplicateException.class,
				() -> userService.register("Lisa",
						"Smit", LocalDate.now().minusMonths(3),
						"example@example.com", "5678",
						"0676462648", "Italiaans"),
				"Email zou niet opnieuw geregistreerd moeten kunnen worden"
		);
	}
}
