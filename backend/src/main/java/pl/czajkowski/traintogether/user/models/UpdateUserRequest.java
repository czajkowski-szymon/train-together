package pl.czajkowski.traintogether.user.models;

import java.time.LocalDate;

public record UpdateUserRequest(
        Integer userId,
        String username,
        String email,
        LocalDate dateOfBirth,
        Gender gender,
        String bio,
        String city
) {
}
