package pl.czajkowski.traintogether.friendship.models;

public record FriendshipInvitationRequest(
        Integer senderId,
        Integer receiverId
) {
}
