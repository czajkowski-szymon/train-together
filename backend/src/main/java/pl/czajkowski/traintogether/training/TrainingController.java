package pl.czajkowski.traintogether.training;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.czajkowski.traintogether.training.models.Training;
import pl.czajkowski.traintogether.training.models.TrainingDTO;
import pl.czajkowski.traintogether.training.models.TrainingUpdateRequest;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping("/{trainingId}")
    public ResponseEntity<TrainingDTO> getTraining(@PathVariable Integer trainingId, Authentication user) {
        return ResponseEntity.ok(trainingService.getTraining(trainingId, user.getName()));
    }

    @PutMapping
    public ResponseEntity<TrainingDTO> updateTraining(@RequestBody TrainingUpdateRequest training, Authentication user) {
        return ResponseEntity.ok(trainingService.updateTraining(training, user.getName()));
    }

    @DeleteMapping("/{trainingId}")
    public ResponseEntity<?> deleteTraining(@PathVariable Integer trainingId, Authentication user) {
        trainingService.deleteTraining(trainingId, user.getName());
        return ResponseEntity.noContent().build();
    }
}
