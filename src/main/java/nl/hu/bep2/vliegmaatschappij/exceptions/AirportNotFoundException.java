package nl.hu.bep2.vliegmaatschappij.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "There is no airport with that ID!")
public class AirportNotFoundException extends Exception{
    public AirportNotFoundException(String msg) {
            super(msg);
        }
}
