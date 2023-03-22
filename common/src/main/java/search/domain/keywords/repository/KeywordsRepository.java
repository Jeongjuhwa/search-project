package search.domain.keywords.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import search.domain.keywords.entity.Keywords;

@Repository
public interface KeywordsRepository extends JpaRepository<Keywords, Long>, KeywordsRepositoryCustom {
    Optional<Keywords> findByWord(String word);

}
