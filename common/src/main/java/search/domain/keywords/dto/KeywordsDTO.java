package search.domain.keywords.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeywordsDTO {
    private Long id;
    private String word;
    private String wordCount;

    public static KeywordsDTO of(Long id, String word, String wordCount){
        return new KeywordsDTO(id, word, wordCount);
    }
}
