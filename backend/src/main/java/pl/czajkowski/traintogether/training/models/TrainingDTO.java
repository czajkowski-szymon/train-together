package pl.czajkowski.traintogether.training.models;

import pl.czajkowski.traintogether.sport.Sport;
import pl.czajkowski.traintogether.user.models.UserDTO;

import java.time.LocalDate;

public record TrainingDTO(
        Integer trainingId,
        LocalDate date,
        Sport sport,
        UserDTO participantOne,
        UserDTO participantTwo
) {
}
