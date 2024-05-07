package pl.czajkowski.traintogether.friendship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.czajkowski.traintogether.friendship.models.FriendshipInvitation;
import pl.czajkowski.traintogether.training.models.TrainingInvitation;

import java.util.List;

@Repository
public interface FriendshipInvitationRepository extends JpaRepository<FriendshipInvitation, Integer> {

    @Query("SELECT f FROM FriendshipInvitation f WHERE f.sender = ?1")
    List<TrainingInvitation> findAllSentFriendshipInvitationsByUsername(String username);

    @Query("SELECT f FROM FriendshipInvitation f WHERE f.receiver = ?1")
    List<TrainingInvitation> findAllReceivedFriendshipInvitationsByUsername(String username);
}
