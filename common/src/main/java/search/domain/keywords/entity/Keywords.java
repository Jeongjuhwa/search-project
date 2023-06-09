package search.domain.keywords.entity;

import com.google.common.base.Joiner;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import search.support.Constant;

@Entity
@Getter
@Table(name = "keywords")
@NoArgsConstructor
public class Keywords {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    private Long wordCount;

    public static Keywords ofDefault(String query) {
        Keywords instance = new Keywords();
        instance.word = query;
        instance.wordCount = 1L;
        return instance;
    }


    public String getRedisCountKey() {
        return Joiner.on(":").join(Constant.REDIS_WORD_COUNT_PREFIX, getId());
    }

    public void updateWordCount(long count) {
        this.wordCount = count;
    }
}
