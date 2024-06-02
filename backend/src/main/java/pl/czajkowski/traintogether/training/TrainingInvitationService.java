package pl.czajkowski.traintogether.training;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.exception.ResourceNotFoundException;
import pl.czajkowski.traintogether.exception.TrainingOwnershipException;
import pl.czajkowski.traintogether.exception.UserNotFoundException;
import pl.czajkowski.traintogether.rabbitmq.RabbitMQConfig;
import pl.czajkowski.traintogether.sport.SportRepository;
import pl.czajkowski.traintogether.training.models.*;
import pl.czajkowski.traintogether.user.UserRepository;
import pl.czajkowski.traintogether.user.models.User;

import java.util.List;

@Service
public class TrainingInvitationService {

    private final TrainingInvitationRepository trainingInvitationRepository;

    private final TrainingRepository trainingRepository;

    private final UserRepository userRepository;

    private final SportRepository sportRepository;

    private final TrainingMapper trainingMapper;

    private final RabbitTemplate template;

    public TrainingInvitationService(TrainingInvitationRepository trainingInvitationRepository,
                                     TrainingRepository trainingRepository,
                                     UserRepository userRepository,
                                     SportRepository sportRepository,
                                     TrainingMapper trainingMapper,
                                     RabbitTemplate template) {
        this.trainingInvitationRepository = trainingInvitationRepository;
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
        this.sportRepository = sportRepository;
        this.trainingMapper = trainingMapper;
        this.template = template;
    }

    public TrainingInvitationDTO addTrainingInvitation(TrainingInvitationRequest request) {
        User sender = userRepository.findById(request.senderId()).orElseThrow(
                () -> new UserNotFoundException("User wit id: %d not found".formatted(request.senderId()))
        );
        User receiver = userRepository.findById(request.receiverId()).orElseThrow(
                () -> new UserNotFoundException("User wit id: %d not found".formatted(request.receiverId()))
        );
        TrainingInvitation invitation = new TrainingInvitation();
        invitation.setDate(request.date());
        invitation.setSport(sportRepository.findSportByName(request.sport()).orElseThrow(
                () -> new ResourceNotFoundException("Sport not found")
        ));
        invitation.setMessage(request.message());
        invitation.setSender(sender);
        invitation.setReceiver(receiver);

        TrainingInvitationMessage tim = new TrainingInvitationMessage(
                request.date(),
                request.sport(),
                request.message(),
                sender.getFirstName(),
                receiver.getFirstName(),
                receiver.getEmail()
        );
        template.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, tim);

        return trainingMapper.toTrainingInvitationDTO(trainingInvitationRepository.save(invitation));
    }

    public TrainingInvitationDTO getTrainingInvitation(Integer invitationId, String username) {
        TrainingInvitation invitation = trainingInvitationRepository.findById(invitationId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Training invitation with id: %d not found".formatted(invitationId)
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
                .peek(System.out::println)
                .map(trainingMapper::toTrainingInvitationDTO)
                .toList();
    }

    public TrainingDTO acceptTrainingInvitation(Integer invitationId, String username) {
        TrainingInvitation invitation = trainingInvitationRepository.findById(invitationId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Training invitation with id: %d not found".formatted(invitationId)
                        )
                );
        validateTrainingInvitationOwnership(invitation, username);
        trainingInvitationRepository.deleteById(invitationId);

        Training training = new Training();
        training.setDate(invitation.getDate());
        training.setSport(invitation.getSport());
        training.setParticipantOne(invitation.getSender());
        training.setParticipantTwo(invitation.getReceiver());

        return trainingMapper.toTrainingDTO(trainingRepository.save(training));
    }

    public void declineTrainingInvitation(Integer invitationId, String username) {
        TrainingInvitation invitation = trainingInvitationRepository.findById(invitationId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Training invitation with id: %d not found".formatted(invitationId)
                        )
                );
        validateTrainingInvitationOwnership(invitation, username);
        trainingInvitationRepository.deleteById(invitationId);
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
