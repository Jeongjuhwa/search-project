package search.domain.keywords.repository.impl;

import static search.domain.keywords.entity.QKeywords.keywords;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import search.domain.keywords.entity.Keywords;
import search.domain.keywords.repository.KeywordsRepositoryCustom;
import search.support.Constant;

public class KeywordsRepositoryCustomImpl extends QuerydslRepositorySupport implements
        KeywordsRepositoryCustom {



    public KeywordsRepositoryCustomImpl(){
        super(Keywords.class);
    }

    @Override
    public List<Keywords> findAllByRankSize() {
        return from(keywords)
                .orderBy(keywords.wordCount.desc())
                .limit(Constant.RANK_SIZE)
                .fetch();
    }

    @Override
    public List<Keywords> findAllByIdIn(Set<Long> keywordsId) {
        return from(keywords)
                .where(
                        keywords.id.in(keywordsId)
                ).fetch();
    }
}
