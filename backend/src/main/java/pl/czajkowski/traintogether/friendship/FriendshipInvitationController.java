package pl.czajkowski.traintogether.friendship;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.czajkowski.traintogether.friendship.models.FriendshipInvitation;
import pl.czajkowski.traintogether.training.models.TrainingInvitation;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/friendships/invitations")
public class FriendshipInvitationController {

    private final FriendshipInvitationService friendshipInvitationService;

    public FriendshipInvitationController(FriendshipInvitationService friendshipInvitationService) {
        this.friendshipInvitationService = friendshipInvitationService;
    }

    @PostMapping
    public ResponseEntity<FriendshipInvitation> addFriendshipInvitation(@RequestBody FriendshipInvitation invitation) {
        return ResponseEntity.created(URI.create( "/api/v1/trainings/invitations"))
                .body(friendshipInvitationService.addFriendshipInvitation(invitation));
    }

    @GetMapping("/{invitationId}")
    public ResponseEntity<FriendshipInvitation> getFriendshipInvitation(@PathVariable Integer invitationId,
                                                                    Authentication user) {
        return ResponseEntity.ok(friendshipInvitationService.getFriendshipInvitation(invitationId, user.getName()));
    }

    @GetMapping("/sent")
    public ResponseEntity<List<TrainingInvitation>> getSentFriendshipInvitationsForUser(Authentication user) {
        return ResponseEntity.ok(friendshipInvitationService.getSentFriendshipInvitationsForUserString(user.getName()));
    }

    @GetMapping("/received")
    public ResponseEntity<List<TrainingInvitation>> getReceivedFriendshipInvitationsForUser(Authentication user) {
        return ResponseEntity.ok(
                friendshipInvitationService.getReceivedFriendshipInvitationsForUserString(user.getName())
        );
    }

    @DeleteMapping("/{invitationId}")
    public ResponseEntity<?> deleteFriendshipInvitation(@PathVariable Integer invitationId, Authentication user) {
        friendshipInvitationService.deleteFriendshipInvitation(invitationId, user.getName());
        return ResponseEntity.noContent().build();
    }
}
