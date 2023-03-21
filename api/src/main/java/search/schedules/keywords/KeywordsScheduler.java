package search.schedules.keywords;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import search.domain.keywords.entity.Keywords;
import search.domain.keywords.repository.KeywordsRepository;
import search.support.RedisService;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeywordsScheduler {

    private final RedisService redisService;
    private final KeywordsRepository keywordsRepository;

    @Scheduled(cron = "0 0/5 * * * ?")
    @Transactional
    public void updateRedis() {
        log.info("batch start!!");

        List<CompletableFuture<Boolean>> completableFutures = new ArrayList<>();
        Set<String> keys = redisService.getKeys("wordCount*");

        Set<Long> ids = new HashSet<>();

        keys.forEach(s -> {
            long keywordId = Long.parseLong(s.split(":")[1]);
            ids.add(keywordId);
            if (ids.size() % 10000 == 0) {
                completableFutures.add(update(ids));
                ids.clear();
            }
        });

        if (!ids.isEmpty()) {
            completableFutures.add(update(ids));
        }

        CompletableFuture.allOf(
                completableFutures.toArray(new CompletableFuture[0])
        ).thenApply(Void -> completableFutures.stream().map(CompletableFuture::join).collect(
                Collectors.toList())).thenAccept(aVoid -> log.info("batch end!!")).join();
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Boolean> update(Set<Long> keywordsId) {
        List<Keywords> keywords = keywordsRepository.findAllByIdIn(keywordsId);
        return CompletableFuture.supplyAsync(() -> {
            keywords.forEach(k -> {
                long count = Long.parseLong(redisService.get(k.getKeys()));
                k.updateWordCount(count);
                redisService.remove(k.getKeys());
            });
            return null;
        });
    }
}
