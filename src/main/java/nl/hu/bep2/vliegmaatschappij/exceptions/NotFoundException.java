package nl.hu.bep2.vliegmaatschappij.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found exception")
public class NotFoundException extends RuntimeException {
	public NotFoundException(String msg) {
		super(msg);
	}

}