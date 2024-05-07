package pl.czajkowski.traintogether.friendship;

import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.exception.ResourceNotFoundException;
import pl.czajkowski.traintogether.exception.TrainingOwnershipException;
import pl.czajkowski.traintogether.friendship.models.FriendshipInvitation;
import pl.czajkowski.traintogether.training.models.TrainingInvitation;

import java.util.List;

@Service
public class FriendshipInvitationService {

    private final FriendshipInvitationRepository friendshipInvitationRepository;

    public FriendshipInvitationService(FriendshipInvitationRepository friendshipInvitationRepository) {
        this.friendshipInvitationRepository = friendshipInvitationRepository;
    }

    public FriendshipInvitation addFriendshipInvitation(FriendshipInvitation invitation) {
        return friendshipInvitationRepository.save(invitation);
    }

    public FriendshipInvitation getFriendshipInvitation(Integer friendshipInvitationId, String username) {
        FriendshipInvitation invitation = friendshipInvitationRepository.findById(friendshipInvitationId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Training invitation with id: %d not found".formatted(friendshipInvitationId)
                        )
                );

        validateFriendshipInvitationOwnership(invitation, username);

        return invitation;
    }

    public List<TrainingInvitation> getSentFriendshipInvitationsForUserString(String username) {
        return friendshipInvitationRepository.findAllSentFriendshipInvitationsByUsername(username);
    }


    public List<TrainingInvitation> getReceivedFriendshipInvitationsForUserString(String username) {
        return friendshipInvitationRepository.findAllReceivedFriendshipInvitationsByUsername(username);
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
