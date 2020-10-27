package nl.hu.bep2.vliegmaatschappij;

import nl.hu.bep2.vliegmaatschappij.data.SpringFlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestClassTest {
	@Autowired
	private TestClass testClass;
	@Autowired
	private SpringFlightRepository flightRepository;
	@Test
	void testReturnTrue() {
		assertTrue(testClass.returnTrue());
	}
	@Test
	void testSql() {
		assertTrue(flightRepository.findById(0).isPresent());
	}
}