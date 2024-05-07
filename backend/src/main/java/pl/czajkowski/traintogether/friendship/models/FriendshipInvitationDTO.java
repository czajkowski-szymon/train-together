package pl.czajkowski.traintogether.friendship.models;

import pl.czajkowski.traintogether.user.models.UserDTO;

import java.time.LocalDateTime;

public record FriendshipInvitationDTO(
        Integer friendshipInvitationId,
        LocalDateTime sendAt,
        boolean isAccepted,
        UserDTO sender,
        UserDTO receiver
) {
}
