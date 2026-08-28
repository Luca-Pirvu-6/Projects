package repository;

import com.luca.football.model.Team;
import java.util.List;
import java.util.Optional;

public interface TeamRepository {
    Team save(Team team);
    Optional<Team> findById(Long id);
    Optional<Team> findByShortCode(String shortCode);
    List<Team> findAll();
    boolean deleteById(Long id);
}