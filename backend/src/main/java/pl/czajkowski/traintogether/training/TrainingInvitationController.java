package pl.czajkowski.traintogether.training;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.czajkowski.traintogether.training.models.Training;
import pl.czajkowski.traintogether.training.models.TrainingInvitation;

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
    public ResponseEntity<TrainingInvitation> addTrainingInvitation(TrainingInvitation invitation) {
        return ResponseEntity.created(URI.create( "/api/v1/trainings/invitations"))
                .body(trainingInvitationService.addTrainingInvitation(invitation));
    }

    @GetMapping("/{invitationId}")
    public ResponseEntity<TrainingInvitation> getTrainingInvitation(@PathVariable Integer trainingInvitationId,
                                                                    Authentication user) {
        return ResponseEntity.ok(trainingInvitationService.getTrainingInvitation(trainingInvitationId, user.getName()));
    }

    @GetMapping("/sent")
    public ResponseEntity<List<TrainingInvitation>> getSentTrainingInvitationsForUser(Authentication user) {
        return ResponseEntity.ok(trainingInvitationService.getSentTrainingInvitationsForUserString(user.getName()));
    }

    @GetMapping("/received")
    public ResponseEntity<List<TrainingInvitation>> getReceivedTrainingInvitationsForUser(Authentication user) {
        return ResponseEntity.ok(trainingInvitationService.getReceivedTrainingInvitationsForUserString(user.getName()));
    }

    @DeleteMapping("/{invitationId}")
    public ResponseEntity<?> deleteTrainingInvitation(@PathVariable Integer invitationId, Authentication user) {
        trainingInvitationService.deleteTrainingInvitation(invitationId, user.getName());
        return ResponseEntity.noContent().build();
    }
}
