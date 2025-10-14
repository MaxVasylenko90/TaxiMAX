package dev.mvasylenko.paymentservice.service;

import dev.mvasylenko.core.dto.PaymentHistoryDto;
import dev.mvasylenko.core.enums.PaymentStatus;

import java.util.List;
import java.util.UUID;

public interface PaymentHistoryService {
    void add(UUID paymentId, PaymentStatus status);

    List<PaymentHistoryDto> getPaymentHistory(UUID payementId);
}
