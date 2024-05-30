package pl.czajkowski.traintogether.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class FriendshipInvitationAlreadyExistsException extends RuntimeException {

    public FriendshipInvitationAlreadyExistsException(String message) {
        super(message);
    }
}
