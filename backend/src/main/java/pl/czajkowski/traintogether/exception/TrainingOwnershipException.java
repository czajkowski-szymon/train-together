package pl.czajkowski.traintogether.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class TrainingOwnershipException extends RuntimeException {

    public TrainingOwnershipException(String message) {
        super(message);
    }
}
