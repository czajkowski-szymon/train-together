package pl.czajkowski.traintogether.friendship.models;

import pl.czajkowski.traintogether.user.models.UserDTO;

import java.time.LocalDate;

public record FriendshipDTO(
        Integer friendshipId,
        LocalDate created,
        UserDTO memberOne,
        UserDTO memberTwo
) {
}
