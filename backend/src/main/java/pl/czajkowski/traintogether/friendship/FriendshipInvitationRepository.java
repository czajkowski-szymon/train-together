package pl.czajkowski.traintogether.friendship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.czajkowski.traintogether.friendship.models.FriendshipInvitation;

import java.util.List;

@Repository
public interface FriendshipInvitationRepository extends JpaRepository<FriendshipInvitation, Integer> {

    @Query("SELECT f FROM FriendshipInvitation f WHERE f.sender.username = ?1")
    List<FriendshipInvitation> findAllSentFriendshipInvitationsByUsername(String username);

    @Query("SELECT f FROM FriendshipInvitation f WHERE f.receiver.username = ?1")
    List<FriendshipInvitation> findAllReceivedFriendshipInvitationsByUsername(String username);

    @Query("""
        SELECT COUNT(f) > 0
        FROM FriendshipInvitation f 
        WHERE (f.sender.userId=?1 AND f.receiver.userId=?2)
        OR (f.sender.userId=?2 AND f.receiver.userId=?1)
    """)
    boolean exists(Integer senderId, Integer receiverId);
}
