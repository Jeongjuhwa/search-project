package search.domain.keywords.event.listener;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;
import search.gateway.event.BlogSelectedEvent;


public interface BlogSelectedEventListener {
    @TransactionalEventListener
    @Async("threadPoolTaskExecutor")
    void handleBlogSelectedEvent(BlogSelectedEvent event);

}
