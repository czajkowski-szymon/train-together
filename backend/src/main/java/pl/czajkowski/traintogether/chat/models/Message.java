package pl.czajkowski.traintogether.chat.models;

import jakarta.persistence.*;
import pl.czajkowski.traintogether.user.models.User;

import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    private String content;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", sender=" + sender +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
