package dev.mvasylenko.paymentservice.service;

import dev.mvasylenko.core.dto.PaymentDto;
import dev.mvasylenko.core.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentService {
    /**
     * Process rent car payment
     * @param driverId
     * @param carRentPrice
     * @return PaymentDto object
     */
    PaymentDto processRentPayment(UUID driverId, BigDecimal carRentPrice);

    /**
     * Find payment by id
     * @param paymentId
     * @return PaymentDto object
     */
    PaymentDto getPayment(UUID paymentId);

    /**
     * Change payment status
     * @param paymentId
     * @param status
     */
    void changePaymentStatus(UUID paymentId, PaymentStatus status);
}
