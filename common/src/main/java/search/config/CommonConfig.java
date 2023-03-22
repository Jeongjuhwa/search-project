package search.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import search.support.Constant;

@Configuration
@EnableAsync
public class CommonConfig extends AsyncConfigurerSupport {

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(Constant.CORE_POOL_SIZE);
        taskExecutor.setMaxPoolSize(Constant.MAX_POOL_SIZE);
        taskExecutor.setQueueCapacity(Constant.QUEUE_CAPACITY);
        taskExecutor.setThreadNamePrefix(Constant.THREAD_NAME_PREFIX);
        taskExecutor.initialize();
        return taskExecutor;
    }
}
