package dev.mvasylenko.paymentservice.service;

import java.util.UUID;

public interface IdempotencyService {
    /**
     * Trying to add event/command key to redis database
     * @param key
     * @return true - for new key; false - if key already exist
     */
    boolean tryAcquire(String key);

    /**
     * Marks event/command by key as done
     * @param key
     */
    void markAsDone(String key);
}
