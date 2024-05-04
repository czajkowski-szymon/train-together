package pl.czajkowski.traintogether.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException e, HttpServletRequest request) {
        HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).code();
        ErrorResponse response = new ErrorResponse(
                request.getRequestURI(),
                status.value(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(UsernameOrEmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(UsernameOrEmailAlreadyExistsException e,
                                                         HttpServletRequest request) {
        HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).code();
        ErrorResponse response = new ErrorResponse(
                request.getRequestURI(),
                status.value(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, status);
    }
}
