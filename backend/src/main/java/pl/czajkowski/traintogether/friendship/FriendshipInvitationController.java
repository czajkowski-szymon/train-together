package pl.czajkowski.traintogether.friendship;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.czajkowski.traintogether.friendship.models.FriendshipInvitationDTO;
import pl.czajkowski.traintogether.friendship.models.FriendshipInvitationRequest;

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
    public ResponseEntity<FriendshipInvitationDTO> addFriendshipInvitation(
            @RequestBody FriendshipInvitationRequest invitation
    ) {
        return ResponseEntity.created(URI.create( "/api/v1/trainings/invitations"))
                .body(friendshipInvitationService.addFriendshipInvitation(invitation));
    }

    @GetMapping("/{invitationId}")
    public ResponseEntity<FriendshipInvitationDTO> getFriendshipInvitation(@PathVariable Integer invitationId,
                                                                    Authentication user) {
        return ResponseEntity.ok(friendshipInvitationService.getFriendshipInvitation(invitationId, user.getName()));
    }

    @GetMapping("/sent")
    public ResponseEntity<List<FriendshipInvitationDTO>> getSentFriendshipInvitationsForUser(Authentication user) {
        return ResponseEntity.ok(friendshipInvitationService.getSentFriendshipInvitationsForUserString(user.getName()));
    }

    @GetMapping("/received")
    public ResponseEntity<List<FriendshipInvitationDTO>> getReceivedFriendshipInvitationsForUser(Authentication user) {
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
