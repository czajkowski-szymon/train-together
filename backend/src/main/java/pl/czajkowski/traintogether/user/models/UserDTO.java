package pl.czajkowski.traintogether.user.models;

import pl.czajkowski.traintogether.sport.Sport;

import java.time.LocalDate;
import java.util.List;

public record UserDTO(
        Integer userId,
        String username,
        String email,
        LocalDate dateOfBirth,
        Gender gender,
        String bio,
        City city,
        Role role,
        List<Sport> sports
) {
}
