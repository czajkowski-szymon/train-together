package pl.czajkowski.emailservice;

import java.time.LocalDate;

public record TrainingInvitationMessage(
        LocalDate date,
        String sport,
        String message,
        String senderName,
        String receiverName,
        String receiverEmail
) {
}
