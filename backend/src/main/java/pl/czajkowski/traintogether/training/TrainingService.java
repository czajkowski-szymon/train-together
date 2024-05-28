package pl.czajkowski.traintogether.training;

import org.springframework.stereotype.Service;
import pl.czajkowski.traintogether.exception.ResourceNotFoundException;
import pl.czajkowski.traintogether.exception.TrainingOwnershipException;
import pl.czajkowski.traintogether.exception.UserNotFoundException;
import pl.czajkowski.traintogether.sport.SportRepository;
import pl.czajkowski.traintogether.training.models.Training;
import pl.czajkowski.traintogether.training.models.TrainingDTO;
import pl.czajkowski.traintogether.training.models.TrainingUpdateRequest;
import pl.czajkowski.traintogether.user.UserRepository;

import java.util.List;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;

    private final TrainingMapper trainingMapper;

    private final SportRepository sportRepository;

    private final UserRepository userRepository;

    public TrainingService(TrainingRepository trainingRepository,
                           TrainingMapper trainingMapper,
                           SportRepository sportRepository,
                           UserRepository userRepository) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
        this.sportRepository = sportRepository;
        this.userRepository = userRepository;
    }

    public TrainingDTO getTraining(Integer trainingId, String username) {
        Training training = trainingRepository.findById(trainingId).orElseThrow(
                () -> new ResourceNotFoundException("Training with id: %d not found".formatted(trainingId))
        );
        validateTrainingOwnership(training, username);

        return trainingMapper.toTrainingDTO(training);
    }

    public List<TrainingDTO> getUpcomingTrainings(String username) {
        return trainingRepository.findAllUpcomingTrainingsForUser(username)
                .stream()
                .map(trainingMapper::toTrainingDTO)
                .toList();
    }

    public List<TrainingDTO> getPastTrainings(String username) {
        return trainingRepository.findAllPastTrainingsForUser(username)
                .stream()
                .map(trainingMapper::toTrainingDTO)
                .toList();
    }

    public TrainingDTO updateTraining(TrainingUpdateRequest request, String username) {
        Training training = trainingRepository.findById(request.trainingId()).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Training with id: %d not found".formatted(request.trainingId())
                )
        );
        validateTrainingOwnership(training, username);

        training.setDate(request.date());
        training.setSport(sportRepository.findSportByName(request.sport()).orElseThrow(
                () -> new ResourceNotFoundException("Sport not found")
        ));
        training.setParticipantOne(userRepository.findById(request.participantOneId()).orElseThrow(
                () -> new UserNotFoundException("User with id: %d not found".formatted(request.participantOneId()))
        ));
        training.setParticipantTwo(userRepository.findById(request.participantTwoId()).orElseThrow(
                () -> new UserNotFoundException("User with id: %d not found".formatted(request.participantTwoId()))
        ));


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