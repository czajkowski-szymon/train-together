package pl.czajkowski.traintogether.training.models;

import jakarta.persistence.*;
import pl.czajkowski.traintogether.sport.Sport;
import pl.czajkowski.traintogether.user.models.User;

import java.time.LocalDate;

@Entity
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainingId;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private Sport sport;

    @ManyToOne
    @JoinColumn(name = "participant1_id")
    private User participantOne;

    @ManyToOne
    @JoinColumn(name = "participant2_id")
    private User participantTwo;

    public Integer getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
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
        return "Training{" +
                "trainingId=" + trainingId +
                ", date=" + date +
                ", sport=" + sport +
                ", participantOne=" + participantOne +
                ", participantTwo=" + participantTwo +
                '}';
    }
}
