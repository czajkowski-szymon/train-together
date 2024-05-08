package pl.czajkowski.traintogether.friendship;

import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.exception.ResourceNotFoundException;
import pl.czajkowski.traintogether.exception.TrainingOwnershipException;
import pl.czajkowski.traintogether.exception.UserNotFoundException;
import pl.czajkowski.traintogether.friendship.models.FriendshipInvitation;
import pl.czajkowski.traintogether.friendship.models.FriendshipInvitationDTO;
import pl.czajkowski.traintogether.friendship.models.FriendshipInvitationRequest;
import pl.czajkowski.traintogether.training.models.TrainingInvitation;
import pl.czajkowski.traintogether.training.models.TrainingInvitationDTO;
import pl.czajkowski.traintogether.user.UserMapper;
import pl.czajkowski.traintogether.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FriendshipInvitationService {

    private final FriendshipInvitationRepository friendshipInvitationRepository;

    private final UserRepository userRepository;

    private final FriendshipMapper friendshipMapper;

    public FriendshipInvitationService(FriendshipInvitationRepository friendshipInvitationRepository,
                                       UserRepository userRepository,
                                       FriendshipMapper friendshipMapper) {
        this.friendshipInvitationRepository = friendshipInvitationRepository;
        this.userRepository = userRepository;
        this.friendshipMapper = friendshipMapper;
    }

    public FriendshipInvitationDTO addFriendshipInvitation(FriendshipInvitationRequest request) {
        FriendshipInvitation invitation = new FriendshipInvitation();
        invitation.setSendAt(LocalDateTime.now());
        invitation.setAccepted(false);
        invitation.setSender(userRepository.findById(request.senderId()).orElseThrow(
                () -> new UserNotFoundException("User wit id: %d not found".formatted(request.senderId()))
        ));
        invitation.setReceiver(userRepository.findById(request.receiverId()).orElseThrow(
                () -> new UserNotFoundException("User wit id: %d not found".formatted(request.receiverId()))
        ));

        return friendshipMapper.toFriendshipInvitationDTO(friendshipInvitationRepository.save(invitation));
    }

    public FriendshipInvitationDTO getFriendshipInvitation(Integer friendshipInvitationId, String username) {
        FriendshipInvitation invitation = friendshipInvitationRepository.findById(friendshipInvitationId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Training invitation with id: %d not found".formatted(friendshipInvitationId)
                        )
                );

        validateFriendshipInvitationOwnership(invitation, username);

        return friendshipMapper.toFriendshipInvitationDTO(invitation);
    }

    public List<FriendshipInvitationDTO> getSentFriendshipInvitationsForUserString(String username) {
        return friendshipInvitationRepository.findAllSentFriendshipInvitationsByUsername(username)
                .stream()
                .map(friendshipMapper::toFriendshipInvitationDTO)
                .toList();
    }


    public List<FriendshipInvitationDTO> getReceivedFriendshipInvitationsForUserString(String username) {
        return friendshipInvitationRepository.findAllReceivedFriendshipInvitationsByUsername(username)
                .stream()
                .map(friendshipMapper::toFriendshipInvitationDTO)
                .toList();
    }

    public void deleteFriendshipInvitation(Integer invitationId, String username) {
        validateFriendshipInvitationSender(invitationId, username);
        friendshipInvitationRepository.deleteById(invitationId);
    }

    private void validateFriendshipInvitationOwnership(FriendshipInvitation invitation, String username) {
        if (!(invitation.getSender().getUsername().equals(username) ||
                invitation.getReceiver().getUsername().equals(username))) {
            throw new TrainingOwnershipException(
                    "Training invitation with id: %d does not belong to user: %s"
                            .formatted(invitation.getFriendshipInvitationId(), username)
            );
        }
    }

    private void validateFriendshipInvitationSender(Integer invitationId, String username) {
        FriendshipInvitation invitation = friendshipInvitationRepository.findById(invitationId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Training invitation with id: %d not found".formatted(invitationId)
                        )
                );

        if (!(invitation.getSender().getUsername().equals(username))) {
            throw new TrainingOwnershipException(
                    "Training invitation with id: %d does not belong to user: %s"
                            .formatted(invitation.getFriendshipInvitationId(), username)
            );
        }
    }
}
