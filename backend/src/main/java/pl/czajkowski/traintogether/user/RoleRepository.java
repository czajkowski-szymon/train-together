package pl.czajkowski.traintogether.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.czajkowski.traintogether.user.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
