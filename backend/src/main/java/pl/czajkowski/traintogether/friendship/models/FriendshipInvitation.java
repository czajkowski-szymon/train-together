package pl.czajkowski.traintogether.friendship.models;

import jakarta.persistence.*;
import pl.czajkowski.traintogether.user.models.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "friendship_invitation")
public class FriendshipInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer friendshipInvitationId;

    private LocalDateTime sendAt;

    private boolean isAccepted;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    public Integer getFriendshipInvitationId() {
        return friendshipInvitationId;
    }

    public void setFriendshipInvitationId(Integer friendshipInvitationId) {
        this.friendshipInvitationId = friendshipInvitationId;
    }

    public LocalDateTime getSendAt() {
        return sendAt;
    }

    public void setSendAt(LocalDateTime sendAt) {
        this.sendAt = sendAt;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "FriendshipInvitation{" +
                "friendshipInvitationId=" + friendshipInvitationId +
                ", sendAt=" + sendAt +
                ", isAccepted=" + isAccepted +
                ", sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }
}
