package search.domain.teams.controller.v1;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import search.domain.teams.service.TeamsService;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamsController {

    private final TeamsService teamsService;

    @DeleteMapping("/{teamId}/members")
    public ResponseEntity<HttpStatus> kickedMembers(
            @PathVariable Long teamId,
            @RequestParam List<Long> membersId
    ){
        teamsService.kickedMembers(teamId, membersId);
        return ResponseEntity.ok(HttpStatus.OK);
    }



}
