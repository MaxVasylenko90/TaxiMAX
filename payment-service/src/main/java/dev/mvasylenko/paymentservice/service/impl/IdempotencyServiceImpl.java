package dev.mvasylenko.paymentservice.service.impl;

import dev.mvasylenko.paymentservice.service.IdempotencyService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class IdempotencyServiceImpl implements IdempotencyService {
    private final RedisTemplate<UUID, Object> eventsRedisTemplate;
    private final RedisTemplate<UUID, Object> commandsRedisTemplate;
    private static final long TTL_HOURS = 24;

    public IdempotencyServiceImpl(@Qualifier("eventsRedisTemplate") RedisTemplate<UUID, Object> eventsRedisTemplate,
                                  @Qualifier("commandsRedisTemplate") RedisTemplate<UUID, Object> commandsRedisTemplate) {
        this.eventsRedisTemplate = eventsRedisTemplate;
        this.commandsRedisTemplate = commandsRedisTemplate;
    }

    @Override
    public boolean isEventProcessed(UUID key) {
        return eventsRedisTemplate.hasKey(key);
    }

    @Override
    public void markEventAsProcessed(UUID key) {
        eventsRedisTemplate.opsForValue().set(key, true, TTL_HOURS, TimeUnit.HOURS);
    }

    @Override
    public boolean isCommandProcessed(UUID commandKey) {
        return commandsRedisTemplate.hasKey(commandKey);
    }

    @Override
    public void markCommandAsProcessed(UUID commandKey) {
        commandsRedisTemplate.opsForValue().set(commandKey, true, TTL_HOURS, TimeUnit.HOURS);
    }
}
