package search.domain.keywords.controller.v1;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import search.domain.keywords.service.KeywordsService;
import search.domain.keywords.dto.KeywordsDTO;

@RestController
@RequestMapping("/api/v1/keywords")
@RequiredArgsConstructor
public class KeywordsController {
    private final KeywordsService keywordsService;

    @GetMapping("/rank")
    public ResponseEntity<List<KeywordsDTO>> getRank(){
        return ResponseEntity.ok(keywordsService.getRank());
    }

}
