package search.domain.keywords.event.listener.impl;

import search.domain.keywords.event.listener.BlogSelectedEventListener;
import search.gateway.event.BlogSelectedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import search.domain.keywords.entity.Keywords;
import search.domain.keywords.repository.KeywordsRepository;
import search.support.RedisService;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlogSelectedEventListenerImpl implements BlogSelectedEventListener {

    private final KeywordsRepository keywordsRepository;
    private final RedisService redisService;


    @Override
    public void handleBlogSelectedEvent(BlogSelectedEvent event) {
        String query = event.getQuery();
        Keywords keywords = keywordsRepository.findByWord(query)
                .orElseGet(() -> keywordsRepository.save(Keywords.ofDefault(query)));
        if(redisService.isExist(keywords.getRedisCountKey())){
            redisService.increment(keywords.getRedisCountKey());
        }else {
            redisService.set(keywords.getRedisCountKey(), keywords.getWordCount()+1);
        }

    }
}
