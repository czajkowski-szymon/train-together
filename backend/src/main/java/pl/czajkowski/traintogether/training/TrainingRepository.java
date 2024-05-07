package pl.czajkowski.traintogether.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.czajkowski.traintogether.training.models.Training;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Integer> {
}
