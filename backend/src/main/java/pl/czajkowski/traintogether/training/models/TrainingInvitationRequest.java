package pl.czajkowski.traintogether.training.models;

import pl.czajkowski.traintogether.sport.Sport;

import java.time.LocalDate;

public record TrainingInvitationRequest(
        LocalDate date,
        String sport,
        Integer senderId,
        Integer receiverId
) {
}
