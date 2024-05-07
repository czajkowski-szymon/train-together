package pl.czajkowski.traintogether.training.models;

import pl.czajkowski.traintogether.sport.Sport;
import pl.czajkowski.traintogether.user.models.UserDTO;

import java.time.LocalDate;

public record TrainingInvitationDTO(
        Integer trainingInvitationId,
        LocalDate date,
        Sport sport,
        boolean isAccepted,
        UserDTO sender,
        UserDTO receiver
) {
}