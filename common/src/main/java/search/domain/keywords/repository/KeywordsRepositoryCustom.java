package search.domain.keywords.repository;


import java.util.List;
import java.util.Set;
import search.domain.keywords.entity.Keywords;


public interface KeywordsRepositoryCustom {
    List<Keywords> findRankKeywords();
    List<Keywords> findAllByIdIn(Set<Long> keywordsId);
}
