package pl.czajkowski.traintogether.friendship.models;

import java.time.LocalDateTime;

public record FriendshipInvitationRequest(
        Integer senderId,
        Integer receiverId
) {
}
