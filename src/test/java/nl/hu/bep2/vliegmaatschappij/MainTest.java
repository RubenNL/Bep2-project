package nl.hu.bep2.vliegmaatschappij;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MainTest {
	@Autowired
	private Main main;
	@Test
	void testReturnTrue() {
		assertTrue(main.returnTrue());
	}
}