package pl.czajkowski.traintogether.friendship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.czajkowski.traintogether.friendship.models.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    @Query("""
        SELECT COUNT(f) > 0
        FROM Friendship f 
        WHERE (f.memberOne.userId=?1 AND f.memberTwo.userId=?2)
        OR (f.memberOne.userId=?2 AND f.memberTwo.userId=?1)
    """)
    boolean exists(Integer senderId, Integer receiverId);
}
