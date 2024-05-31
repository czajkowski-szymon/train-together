package pl.czajkowski.traintogether.sport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SportRepository extends JpaRepository<Sport, Integer> {

    Optional<Sport> findSportByName(String name);
}
