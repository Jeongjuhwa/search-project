package search.domain.keywords.repository;


import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import search.domain.keywords.entity.Keywords;

@Repository
public interface KeywordsRepository extends JpaRepository<Keywords, Long>, KeywordsRepositoryCustom {

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Keywords> findByWord(String word);

}
