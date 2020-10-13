package nl.hu.bep2.vliegmaatschappij.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "There is no plane with that ID!")
public class PlaneNotFoundException extends Exception {
	public PlaneNotFoundException(String msg) {
		super(msg);
	}

}
