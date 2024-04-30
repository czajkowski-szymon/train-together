package pl.czajkowski.traintogether.auth.models;

import pl.czajkowski.traintogether.user.models.UserDTO;

public record LoginResponse(UserDTO user, String token) {
}
