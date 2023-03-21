package search.domain.keywords.service.impl;

import search.domain.keywords.service.KeywordsService;
import search.support.RedisService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import search.domain.keywords.dto.KeywordsDTO;
import search.domain.keywords.entity.Keywords;
import search.domain.keywords.repository.KeywordsRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordsServiceImpl implements KeywordsService {

    private final KeywordsRepository keywordsRepository;
    private final RedisService redisService;

    @Override
    public List<KeywordsDTO> getRank() {

        List<Keywords> rankKeywords = keywordsRepository.findRankKeywords();
        return rankKeywords.stream().map(k -> {
            String wordCount;
            if(redisService.isExist(k.getKeys())){
                wordCount = redisService.get(k.getKeys());
            }else{
                wordCount = String.valueOf(k.getWordCount());
                redisService.set(k.getKeys(), k.getWordCount());
            }
            return KeywordsDTO.of(k.getId(), k.getWord(), wordCount);
        }).collect(
                Collectors.toList());
    }
}
