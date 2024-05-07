package pl.czajkowski.traintogether.training;

import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.exception.ResourceNotFoundException;
import pl.czajkowski.traintogether.exception.TrainingOwnershipException;
import pl.czajkowski.traintogether.training.models.Training;
import pl.czajkowski.traintogether.training.models.TrainingDTO;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;

    private final TrainingMapper trainingMapper;

    public TrainingService(TrainingRepository trainingRepository, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
    }

    public TrainingDTO getTraining(Integer trainingId, String username) {
        Training training = trainingRepository.findById(trainingId).orElseThrow(
                () -> new ResourceNotFoundException("Training with id: %d not found".formatted(trainingId))
        );
        validateTrainingOwnership(training, username);

        return trainingMapper.toTrainingDTO(training);
    }

    public TrainingDTO updateTraining(Training training, String username) {
        Training trainingFromDb = trainingRepository.findById(training.getTrainingId()).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Training with id: %d not found".formatted(training.getTrainingId())
                )
        );
        validateTrainingOwnership(training, username);

        return trainingMapper.toTrainingDTO(trainingRepository.save(training));
    }

    public void deleteTraining(Integer trainingId, String username) {
        validateTrainingOwnership(trainingId, username);

        trainingRepository.deleteById(trainingId);
    }

    private void validateTrainingOwnership(Training training, String username) {
        if (!(training.getParticipantOne().getUsername().equals(username) ||
                training.getParticipantTwo().getUsername().equals(username))) {
            throw new TrainingOwnershipException(
                    "Training with id: %d does not belong to user: %s".formatted(training.getTrainingId(), username)
            );
        }
    }

    private void validateTrainingOwnership(Integer trainingId, String username) {
        Training training = trainingRepository.findById(trainingId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Training with id: %d not found".formatted(trainingId)
                )
        );

        if (!(training.getParticipantOne().getUsername().equals(username) ||
                training.getParticipantTwo().getUsername().equals(username))) {
            throw new TrainingOwnershipException(
                    "Training with id: %d does not belong to user: %s".formatted(training.getTrainingId(), username)
            );
        }
    }
}