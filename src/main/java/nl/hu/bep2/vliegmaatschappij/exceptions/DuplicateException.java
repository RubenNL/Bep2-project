package nl.hu.bep2.vliegmaatschappij.exceptions;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate exception")
public class DuplicateException extends DuplicateKeyException {
    public DuplicateException(String msg) {
        super(msg);
    }
}
