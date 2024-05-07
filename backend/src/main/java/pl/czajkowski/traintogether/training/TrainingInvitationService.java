package pl.czajkowski.traintogether.training;

import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.exception.ResourceNotFoundException;
import pl.czajkowski.traintogether.exception.TrainingOwnershipException;
import pl.czajkowski.traintogether.training.models.TrainingInvitation;

import java.util.List;

@Service
public class TrainingInvitationService {

    private final TrainingInvitationRepository trainingInvitationRepository;

    public TrainingInvitationService(TrainingInvitationRepository trainingInvitationRepository) {
        this.trainingInvitationRepository = trainingInvitationRepository;
    }

    public TrainingInvitation addTrainingInvitation(TrainingInvitation invitation) {
        return trainingInvitationRepository.save(invitation);
    }

    public TrainingInvitation getTrainingInvitation(Integer trainingInvitationId, String username) {
        TrainingInvitation invitation = trainingInvitationRepository.findById(trainingInvitationId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Training invitation with id: %d not found".formatted(trainingInvitationId)
                        )
                );
        validateTrainingInvitationOwnership(invitation, username);

        return invitation;
    }

    public List<TrainingInvitation> getSentTrainingInvitationsForUserString(String username) {
        return trainingInvitationRepository.findAllSentTrainingInvitationsByUsername(username);
    }


    public List<TrainingInvitation> getReceivedTrainingInvitationsForUserString(String username) {
        return trainingInvitationRepository.findAllReceivedTrainingInvitationsByUsername(username);
    }

    public void deleteTrainingInvitation(Integer invitationId, String username) {
        validateTrainingInvitationSender(invitationId, username);
        trainingInvitationRepository.deleteById(invitationId);
    }

    private void validateTrainingInvitationOwnership(TrainingInvitation invitation, String username) {
        if (!(invitation.getSender().getUsername().equals(username) ||
                invitation.getReceiver().getUsername().equals(username))) {
            throw new TrainingOwnershipException(
                    "Training invitation with id: %d does not belong to user: %s"
                            .formatted(invitation.getTrainingInvitationId(), username)
            );
        }
    }

    private void validateTrainingInvitationSender(Integer invitationId, String username) {
        TrainingInvitation invitation = trainingInvitationRepository.findById(invitationId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Training invitation with id: %d not found".formatted(invitationId)
                        )
                );

        if (!(invitation.getSender().getUsername().equals(username))) {
            throw new TrainingOwnershipException(
                    "Training invitation with id: %d does not belong to user: %s"
                            .formatted(invitation.getTrainingInvitationId(), username)
            );
        }
    }
}
