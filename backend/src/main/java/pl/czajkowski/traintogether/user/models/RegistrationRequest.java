package pl.czajkowski.traintogether.user.models;

import java.time.LocalDate;

public record RegistrationRequest(
        String username,
        String email,
        String password,
        LocalDate dateOfBirth,
        Gender gender,
        String bio,
        String city
) {
}
