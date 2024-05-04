package pl.czajkowski.traintogether.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UsernameOrEmailAlreadyExistsException extends RuntimeException {

    public UsernameOrEmailAlreadyExistsException(String message) {
        super(message);
    }
}
