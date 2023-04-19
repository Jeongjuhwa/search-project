package search.domain.keywords.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class KeywordsTest {

    @Test
    @DisplayName("최초 키워드 생성")
    void createDefaultKeywordsTest(){
        // given
        String query = "최초";

        // when
        Keywords keywords = Keywords.ofDefault(query);

        //then
        assertThat(keywords.getWord()).isEqualTo(query);
        assertThat(keywords.getWordCount()).isEqualTo(1L);
    }

    @Test
    @DisplayName("워드카운트 수정")
    void updateWordCountTest(){
        // given
        String query = "최초";
        Keywords keywords = Keywords.ofDefault(query);
        // when
        keywords.updateWordCount(5);
        // then
        assertThat(keywords.getWordCount()).isEqualTo(5);
    }

}