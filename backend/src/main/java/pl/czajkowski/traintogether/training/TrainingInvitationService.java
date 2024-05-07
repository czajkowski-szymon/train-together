package pl.czajkowski.traintogether.training;

import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.exception.ResourceNotFoundException;
import pl.czajkowski.traintogether.exception.TrainingOwnershipException;
import pl.czajkowski.traintogether.training.models.TrainingInvitation;
import pl.czajkowski.traintogether.training.models.TrainingInvitationDTO;
import pl.czajkowski.traintogether.user.UserMapper;

import java.util.List;

@Service
public class TrainingInvitationService {

    private final TrainingInvitationRepository trainingInvitationRepository;

    private final TrainingMapper trainingMapper;

    public TrainingInvitationService(TrainingInvitationRepository trainingInvitationRepository,
                                     TrainingMapper trainingMapper) {
        this.trainingInvitationRepository = trainingInvitationRepository;
        this.trainingMapper = trainingMapper;
    }

    public TrainingInvitationDTO addTrainingInvitation(TrainingInvitation invitation) {
        return trainingMapper.toTrainingInvitationDTO(trainingInvitationRepository.save(invitation));
    }

    public TrainingInvitationDTO getTrainingInvitation(Integer trainingInvitationId, String username) {
        TrainingInvitation invitation = trainingInvitationRepository.findById(trainingInvitationId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Training invitation with id: %d not found".formatted(trainingInvitationId)
                        )
                );
        validateTrainingInvitationOwnership(invitation, username);

        return trainingMapper.toTrainingInvitationDTO(invitation);
    }

    public List<TrainingInvitationDTO> getSentTrainingInvitationsForUserString(String username) {
        return trainingInvitationRepository.findAllSentTrainingInvitationsByUsername(username)
                .stream()
                .map(trainingMapper::toTrainingInvitationDTO)
                .toList();
    }


    public List<TrainingInvitationDTO> getReceivedTrainingInvitationsForUserString(String username) {
        return trainingInvitationRepository.findAllReceivedTrainingInvitationsByUsername(username)
                .stream()
                .map(trainingMapper::toTrainingInvitationDTO)
                .toList();
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
