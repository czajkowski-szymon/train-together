package pl.czajkowski.traintogether.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.czajkowski.traintogether.user.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);
}
