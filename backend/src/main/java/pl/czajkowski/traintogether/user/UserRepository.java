package pl.czajkowski.traintogether.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.czajkowski.traintogether.city.models.City;
import pl.czajkowski.traintogether.user.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username != :username AND u.enabled = true")
    List<User> findAllExceptGivenUser(String username);

    @Query("SELECT u FROM User u WHERE u.city = :city AND u.username != :username AND u.enabled = true ")
    List<User> findAllByCityExceptGivenUser(City city, String username);

    boolean existsByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);

    @Query(value = """
        SELECT
            u.*
        FROM
            friendship f
        JOIN
            user_ u ON (u.user_id = CASE WHEN f.member1_id = :userId THEN f.member2_id ELSE f.member1_id END)
        WHERE
            :userId IN (f.member1_id, f.member2_id)
        AND
            u.enabled = true;
    """, nativeQuery = true)
    List<User> findAllFriendsOfUser(Integer userId);
}
