package pl.czajkowski.traintogether.training.models;

import java.time.LocalDate;

public record TrainingUpdateRequest(
        Integer trainingId,
        LocalDate date,
        String sport,
        Integer participantOneId,
        Integer participantTwoId
) {
}
