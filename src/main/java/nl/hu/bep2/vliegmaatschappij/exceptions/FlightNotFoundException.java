package nl.hu.bep2.vliegmaatschappij.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "There is no Flight with that ID!")
public class FlightNotFoundException extends RuntimeException {
	public FlightNotFoundException(String msg) {
		super(msg);
	}
}