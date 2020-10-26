package nl.hu.bep2.vliegmaatschappij.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "There is no FlightRoute with that ID!")
public class FlightRouteNotFoundException extends RuntimeException {
	public FlightRouteNotFoundException(String msg) {
		super(msg);
	}
}
