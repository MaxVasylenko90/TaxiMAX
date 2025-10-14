package dev.mvasylenko.paymentservice.service.impl;

import dev.mvasylenko.core.dto.PaymentHistoryDto;
import dev.mvasylenko.core.enums.PaymentStatus;
import dev.mvasylenko.paymentservice.entity.PaymentHistory;
import dev.mvasylenko.paymentservice.mapper.PaymentHistoryMapper;
import dev.mvasylenko.paymentservice.repository.PaymentHistoryRepository;
import dev.mvasylenko.paymentservice.service.PaymentHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;

    public PaymentHistoryServiceImpl(PaymentHistoryRepository paymentHistoryRepository) {
        this.paymentHistoryRepository = paymentHistoryRepository;
    }

    @Override
    @Transactional
    public void add(UUID paymentId, PaymentStatus status) {
        PaymentHistory paymentHistory = new PaymentHistory(paymentId, LocalDateTime.now(), status);
        paymentHistoryRepository.save(paymentHistory);
    }

    @Override
    public List<PaymentHistoryDto> getPaymentHistory(UUID paymentId) {
        var histories = paymentHistoryRepository.findByPaymentId(paymentId);
        return histories.stream().map(PaymentHistoryMapper.INSTANCE::historyToHistoryDto).toList();
    }
}
