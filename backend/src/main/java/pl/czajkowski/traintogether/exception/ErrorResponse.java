package pl.czajkowski.traintogether.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
        String path,
        int status,
        String message,
        LocalDateTime timestamp
) {
}
