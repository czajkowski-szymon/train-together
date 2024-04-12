package pl.czajkowski.traintogether.training;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.czajkowski.traintogether.training.models.Training;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/trainings")
public class TrainingController {

    @PostMapping
    public ResponseEntity<Training> addTraining(Training training) {
        return ResponseEntity.created(URI.create( "/api/v1/trainings")).body(null);
    }

    @GetMapping("/{trainingId}")
    public ResponseEntity<Training> getTraining(@PathVariable Integer trainingId) {
        return ResponseEntity.ok(null);
    }

    @PutMapping
    public ResponseEntity<Training> updateTraining(Training training) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{trainingId}")
    public ResponseEntity<?> deleteTraining(@PathVariable Integer trainingId) {
        return ResponseEntity.noContent().build();
    }
}
