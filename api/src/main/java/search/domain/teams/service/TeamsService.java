package search.domain.teams.service;

import java.util.List;

public interface TeamsService {

    void kickedMembers(Long teamId, List<Long> membersId);
}
