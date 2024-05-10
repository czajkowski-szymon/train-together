package pl.czajkowski.traintogether.training;

import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.exception.ResourceNotFoundException;
import pl.czajkowski.traintogether.exception.TrainingOwnershipException;
import pl.czajkowski.traintogether.exception.UserNotFoundException;
import pl.czajkowski.traintogether.training.models.TrainingInvitation;
import pl.czajkowski.traintogether.training.models.TrainingInvitationDTO;
import pl.czajkowski.traintogether.training.models.TrainingInvitationRequest;
import pl.czajkowski.traintogether.user.UserRepository;

import java.util.List;

@Service
public class TrainingInvitationService {

    private final TrainingInvitationRepository trainingInvitationRepository;

    private final UserRepository userRepository;

    private final TrainingMapper trainingMapper;

    public TrainingInvitationService(TrainingInvitationRepository trainingInvitationRepository,
                                     UserRepository userRepository,
                                     TrainingMapper trainingMapper) {
        this.trainingInvitationRepository = trainingInvitationRepository;
        this.userRepository = userRepository;
        this.trainingMapper = trainingMapper;
    }

    public TrainingInvitationDTO addTrainingInvitation(TrainingInvitationRequest request) {
        TrainingInvitation invitation = new TrainingInvitation();
        invitation.setDate(request.date());
        invitation.setSport(request.sport());
        invitation.setAccepted(false);
        invitation.setSender(userRepository.findById(request.senderId()).orElseThrow(
                () -> new UserNotFoundException("User wit id: %d not found".formatted(request.senderId()))
        ));
        invitation.setReceiver(userRepository.findById(request.receiverId()).orElseThrow(
                () -> new UserNotFoundException("User wit id: %d not found".formatted(request.receiverId()))
        ));

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
