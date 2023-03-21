package search.domain.keywords.event.listener;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import search.gateway.event.BlogSelectedEvent;


public interface BlogSelectedEventListener {
    @TransactionalEventListener
    @Async("threadPoolTaskExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void handleBlogSelectedEvent(BlogSelectedEvent event);

}
