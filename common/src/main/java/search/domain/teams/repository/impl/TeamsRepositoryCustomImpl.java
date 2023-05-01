package search.domain.teams.repository.impl;

import static search.domain.members.entity.QMembers.members;
import static search.domain.teams.entity.QTeams.teams;

import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import search.domain.teams.entity.Teams;
import search.domain.teams.repository.TeamsRepositoryCustom;

public class TeamsRepositoryCustomImpl extends QuerydslRepositorySupport implements TeamsRepositoryCustom {

    public TeamsRepositoryCustomImpl() {
        super(Teams.class);
    }


    @Override
    public Optional<Teams> findByIdWithMembers(Long teamId) {

        return Optional.ofNullable(from(teams)
                .join(teams.members, members).fetchJoin()
                .where(
                        teams.id.eq(teamId)
                ).fetchOne());
    }
}
