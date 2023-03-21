package search.domain.keywords.service;

import java.util.List;
import search.domain.keywords.dto.KeywordsDTO;

public interface KeywordsService {

    List<KeywordsDTO> getRank();
}
