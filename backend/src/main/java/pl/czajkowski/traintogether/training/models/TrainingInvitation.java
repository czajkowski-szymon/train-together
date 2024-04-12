package pl.czajkowski.traintogether.training.models;

import jakarta.persistence.*;
import pl.czajkowski.traintogether.sport.Sport;
import pl.czajkowski.traintogether.user.models.User;

import java.time.LocalDate;

@Entity
@Table(name = "training_invitation")
public class TrainingInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainingInvitationId;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private Sport sport;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    public Integer getTrainingInvitationId() {
        return trainingInvitationId;
    }

    public void setTrainingInvitationId(Integer trainingInvitationId) {
        this.trainingInvitationId = trainingInvitationId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
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
        return "TrainingInvitation{" +
                "trainingInvitationId=" + trainingInvitationId +
                ", date=" + date +
                ", sport=" + sport +
                ", sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }
}
