package pl.czajkowski.traintogether.sport;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SportRepository extends JpaRepository<Sport, Integer> {

    Optional<Sport> getSportByName(String name);
}
