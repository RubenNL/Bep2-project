package nl.hu.bep2.vliegmaatschappij;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//Temp class for testing.
@Transactional
@Service
public class Main {
	public boolean returnTrue() {
		return true;
	}
}
