package nl.hu.bep2.vliegmaatschappij.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "There are not enough available seats to complete this booking")
public class NotEnoughSeatsException extends RuntimeException {
	public NotEnoughSeatsException(String msg) {
		super(msg);
	}
}
