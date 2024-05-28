package pl.czajkowski.traintogether.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.czajkowski.traintogether.training.models.Training;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Integer> {

    @Query("""
        SELECT t FROM Training t 
        WHERE (t.participantOne.username=:username OR t.participantTwo.username=:username)
        AND t.date >= CURRENT_DATE
    """)
    List<Training> findAllUpcomingTrainingsForUser(String username);

    @Query("""
        SELECT t FROM Training t 
        WHERE (t.participantOne.username=:username OR t.participantTwo.username=:username)
        AND t.date < CURRENT_DATE
    """)
    List<Training> findAllPastTrainingsForUser(String username);
}
