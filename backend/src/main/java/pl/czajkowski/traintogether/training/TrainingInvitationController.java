package pl.czajkowski.traintogether.training;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.czajkowski.traintogether.training.models.Training;
import pl.czajkowski.traintogether.training.models.TrainingInvitation;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trainings/invitations")
public class TrainingInvitationController {

    @PostMapping
    public ResponseEntity<Training> addTrainingInvitation(TrainingInvitation invitation) {
        return ResponseEntity.created(URI.create( "/api/v1/trainings/invitations")).body(null);
    }

    @GetMapping("/{invitationId}")
    public ResponseEntity<Training> getTrainingInvitation(@PathVariable Integer invitationId) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrainingInvitation>> getAllTrainingInvitationsForUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{invitationId}")
    public ResponseEntity<?> deleteTrainingInvitation(@PathVariable Integer invitationId) {
        return ResponseEntity.noContent().build();
    }
}
