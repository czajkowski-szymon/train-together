package pl.czajkowski.traintogether.training;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.czajkowski.traintogether.training.models.TrainingInvitationDTO;
import pl.czajkowski.traintogether.training.models.TrainingInvitationRequest;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trainings/invitations")
public class TrainingInvitationController {

    private final TrainingInvitationService trainingInvitationService;

    public TrainingInvitationController(TrainingInvitationService trainingInvitationService) {
        this.trainingInvitationService = trainingInvitationService;
    }

    @PostMapping
    public ResponseEntity<TrainingInvitationDTO> addTrainingInvitation(
            @RequestBody TrainingInvitationRequest invitation) {
        return ResponseEntity.created(URI.create( "/api/v1/trainings/invitations"))
                .body(trainingInvitationService.addTrainingInvitation(invitation));
    }

    @GetMapping("/{invitationId}")
    public ResponseEntity<TrainingInvitationDTO> getTrainingInvitation(@PathVariable Integer invitationId,
                                                                       Authentication user) {
        return ResponseEntity.ok(trainingInvitationService.getTrainingInvitation(invitationId, user.getName()));
    }

    @GetMapping("/sent")
    public ResponseEntity<List<TrainingInvitationDTO>> getSentTrainingInvitationsForUser(Authentication user) {
        return ResponseEntity.ok(trainingInvitationService.getSentTrainingInvitationsForUserString(user.getName()));
    }

    @GetMapping("/received")
    public ResponseEntity<List<TrainingInvitationDTO>> getReceivedTrainingInvitationsForUser(Authentication user) {
        return ResponseEntity.ok(trainingInvitationService.getReceivedTrainingInvitationsForUserString(user.getName()));
    }

    @PatchMapping("/{invitationId}/accept")
    public ResponseEntity<TrainingInvitationDTO> acceptTrainingInvitation(@PathVariable Integer invitationId,
                                                                          Authentication user) {
        return ResponseEntity.ok(trainingInvitationService.acceptTrainingInvitation(invitationId, user.getName()));
    }

    @PatchMapping("/{invitationId}/decline")
    public ResponseEntity<TrainingInvitationDTO> declineTrainingInvitation(@PathVariable Integer invitationId,
                                                                           Authentication user) {
        return ResponseEntity.ok(trainingInvitationService.declineTrainingInvitation(invitationId, user.getName()));
    }

    @DeleteMapping("/{invitationId}")
    public ResponseEntity<?> deleteTrainingInvitation(@PathVariable Integer invitationId, Authentication user) {
        trainingInvitationService.deleteTrainingInvitation(invitationId, user.getName());
        return ResponseEntity.noContent().build();
    }
}
