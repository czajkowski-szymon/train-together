package pl.czajkowski.traintogether.chat.models;

import jakarta.persistence.*;
import pl.czajkowski.traintogether.user.models.User;

import java.util.List;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chatId;

    @ManyToOne
    @JoinColumn(name = "participant1_id")
    private User participantOne;

    @ManyToOne
    @JoinColumn(name = "participant2_id")
    private User participantTwo;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public User getParticipantOne() {
        return participantOne;
    }

    public void setParticipantOne(User participantOne) {
        this.participantOne = participantOne;
    }

    public User getParticipantTwo() {
        return participantTwo;
    }

    public void setParticipantTwo(User participantTwo) {
        this.participantTwo = participantTwo;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "chatId=" + chatId +
                ", participantOne=" + participantOne +
                ", participantTwo=" + participantTwo +
                '}';
    }
}
