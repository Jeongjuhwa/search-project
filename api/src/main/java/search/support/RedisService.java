package search.support;

import java.lang.reflect.Type;
import java.util.Set;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class RedisService {
    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOperations;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, Object> stringRedisTemplate;

    private final RedisTemplate<String, String> redisTemplate;


    // 기본 key value
    public void set(String key, Object value) {
        stringRedisTemplate.set(key, value);
    }

    // 기본 key value
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Long increment(String key) {
        Long value = stringRedisTemplate.increment(key);
        return value;
    }

    public boolean isExist(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public boolean remove(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public Set<String> getKeys(String pattern){
        return redisTemplate.keys(pattern);
    }

}
