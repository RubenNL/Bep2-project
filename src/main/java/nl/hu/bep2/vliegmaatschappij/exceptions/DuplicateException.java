package nl.hu.bep2.vliegmaatschappij.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Email-Adres is already registered")
public class DuplicateException extends RuntimeException{
	public DuplicateException(String msg) {
		super(msg);
	}
}
