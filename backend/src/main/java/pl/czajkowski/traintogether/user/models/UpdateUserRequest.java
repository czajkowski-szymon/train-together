package pl.czajkowski.traintogether.user.models;

import java.time.LocalDate;

public record UpdateUserRequest(
        String username,
        String email,
        String firstName,
        LocalDate dateOfBirth,
        Gender gender,
        String bio,
        String city
) {
}
