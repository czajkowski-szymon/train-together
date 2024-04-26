package pl.czajkowski.traintogether.friendship.models;

import jakarta.persistence.*;
import pl.czajkowski.traintogether.user.models.User;

import java.time.LocalDate;

@Entity
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer friendshipId;

    private LocalDate created;

    @ManyToOne
    @JoinColumn(name = "member1_id")
    private User memberOne;

    @ManyToOne
    @JoinColumn(name = "member2_id")
    private User memberTwo;

    public Integer getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(Integer friendshipId) {
        this.friendshipId = friendshipId;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public User getMemberOne() {
        return memberOne;
    }

    public void setMemberOne(User memberOne) {
        this.memberOne = memberOne;
    }

    public User getMemberTwo() {
        return memberTwo;
    }

    public void setMemberTwo(User memberTwo) {
        this.memberTwo = memberTwo;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "friendshipId=" + friendshipId +
                ", created=" + created +
                ", memberOne=" + memberOne +
                ", memberTwo=" + memberTwo +
                '}';
    }
}
