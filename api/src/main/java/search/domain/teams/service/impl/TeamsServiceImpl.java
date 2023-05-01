package search.domain.teams.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import search.domain.teams.entity.Teams;
import search.domain.teams.repository.TeamsRepository;
import search.domain.teams.service.TeamsService;
import search.exception.ApiException;
import search.exception.ErrorCode;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamsServiceImpl implements TeamsService {

    private final TeamsRepository teamsRepository;

    @Override
    public void kickedMembers(Long teamId, List<Long> membersId) {
        Teams teams = teamsRepository.findByIdWithMembers(teamId)
                .orElseThrow(() -> ApiException.of(ErrorCode.NOT_FOUND_TEAM));

        teams.kickedMember(membersId);
    }
}
