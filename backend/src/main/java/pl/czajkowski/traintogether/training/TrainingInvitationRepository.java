package pl.czajkowski.traintogether.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.czajkowski.traintogether.training.models.TrainingInvitation;

import java.util.List;

@Repository
public interface TrainingInvitationRepository extends JpaRepository<TrainingInvitation, Integer> {

    @Query("SELECT i FROM TrainingInvitation i WHERE i.sender.username = ?1 AND i.sender.enabled = true")
    List<TrainingInvitation> findAllSentTrainingInvitationsByUsername(String username);

    @Query("SELECT i FROM TrainingInvitation i WHERE i.receiver.username = ?1 AND i.sender.enabled = true")
    List<TrainingInvitation> findAllReceivedTrainingInvitationsByUsername(String username);
}
