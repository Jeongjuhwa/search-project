package search.domain.teams.repository;

import java.util.Optional;
import search.domain.teams.entity.Teams;

public interface TeamsRepositoryCustom {

    Optional<Teams> findByIdWithMembers(Long teamId);

}
