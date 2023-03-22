package search.support;

public class Constant {

    public static final String REDIS_WORD_COUNT_PREFIX= "wordCount";
    public static final String WORD_COUNT_PATTERN = "wordCount*";
    public static final long RANK_SIZE = 10L;

    public static final int CORE_POOL_SIZE = 3;
    public static final int MAX_POOL_SIZE = 30;
    public static final int QUEUE_CAPACITY = 10;
    public static final String THREAD_NAME_PREFIX = "Executor-";
}
