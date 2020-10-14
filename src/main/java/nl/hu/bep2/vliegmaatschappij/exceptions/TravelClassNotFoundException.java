package nl.hu.bep2.vliegmaatschappij.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Travel Class with that ID could not be found")
public class TravelClassNotFoundException extends RuntimeException {
    public TravelClassNotFoundException(String message) {
        super(message);
    }
}
