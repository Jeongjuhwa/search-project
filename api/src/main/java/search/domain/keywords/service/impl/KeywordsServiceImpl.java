package search.domain.keywords.service.impl;

import search.domain.keywords.entity.mapper.KeywordsMapper;
import search.domain.keywords.service.KeywordsService;
import search.support.RedisService;
import java.util.List;
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

    private final KeywordsMapper keywordsMapper;

    @Override
    public List<KeywordsDTO> getRank() {

        List<Keywords> rankKeywords = keywordsRepository.findAllByRankSize();
        return rankKeywords.stream().map(k -> {
            String wordCount;
            if(redisService.isExist(k.getRedisCountKey())){
                wordCount = redisService.get(k.getRedisCountKey());
            }else{
                wordCount = String.valueOf(k.getWordCount());
                redisService.set(k.getRedisCountKey(), k.getWordCount());
            }
            return keywordsMapper.asDTOWithLiveCount(k, wordCount);
        }).collect(
                Collectors.toList());
    }
}
