package search.domain.teams.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import search.domain.teams.entity.Teams;

@Repository
public interface TeamsRepository extends JpaRepository<Teams, Long>, TeamsRepositoryCustom {
}
