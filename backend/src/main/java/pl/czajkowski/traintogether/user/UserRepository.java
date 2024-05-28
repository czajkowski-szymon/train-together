package pl.czajkowski.traintogether.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.czajkowski.traintogether.user.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username != :username")
    List<User> findAllExceptGivenUser(String username);

    boolean existsByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);
}
