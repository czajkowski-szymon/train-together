package pl.czajkowski.traintogether.user.models;

import java.time.LocalDate;

public record RegistrationRequest(
        String username,
        String email,
        String firstName,
        String password,
        LocalDate dateOfBirth,
        Gender gender,
        String bio,
        String city
) {
}
