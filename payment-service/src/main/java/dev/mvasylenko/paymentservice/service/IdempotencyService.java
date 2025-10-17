package dev.mvasylenko.paymentservice.service;

import java.util.UUID;

public interface IdempotencyService {
    /**
     * Check whether event has already processed
     * @param eventKey
     * @return true if command has already processed, otherwith - false
     */
    boolean isEventProcessed(UUID eventKey);

    /**
     * Marking event as processed in redis
     * @param eventKey
     */
    void markEventAsProcessed(UUID eventKey);

    /**
     * Check whether command has already processed
     * @param commandKey
     * @return true if command has already processed, otherwith - false
     */
    boolean isCommandProcessed(UUID commandKey);

    /**
     * Marking command as processed in redis
     * @param commandKey
     */
    void markCommandAsProcessed(UUID commandKey);
}
