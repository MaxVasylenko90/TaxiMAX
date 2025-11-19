package dev.mvasylenko.driverservice.service.impl;

import dev.mvasylenko.driverservice.service.IdempotencyService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

import static dev.mvasylenko.core.constants.CoreConstants.*;

@Service
public class IdempotencyServiceImpl implements IdempotencyService {
    private final RedisTemplate<String, String> redisTemplate;

    public IdempotencyServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean tryAcquire(String key) {
        Boolean ok = redisTemplate.opsForValue().setIfAbsent(key, PROCESSING, REDIS_KEY_TTL_MINUTES, TimeUnit.MINUTES);
        return Boolean.TRUE.equals(ok);
    }

    @Override
    public void markAsDone(String key) {
        redisTemplate.opsForValue().set(key, DONE, REDIS_KEY_TTL_MINUTES, TimeUnit.SECONDS);
    }
}
