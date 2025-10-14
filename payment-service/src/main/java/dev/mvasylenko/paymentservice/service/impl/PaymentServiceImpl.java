package dev.mvasylenko.paymentservice.service.impl;

import dev.mvasylenko.core.dto.PaymentDto;
import dev.mvasylenko.core.dto.PaymentHistoryDto;
import dev.mvasylenko.core.enums.PaymentStatus;
import dev.mvasylenko.paymentservice.entity.Payment;
import dev.mvasylenko.paymentservice.exception.PaymentNotFoundException;
import dev.mvasylenko.paymentservice.mapper.PaymentHistoryMapper;
import dev.mvasylenko.paymentservice.mapper.PaymentMapper;
import dev.mvasylenko.paymentservice.repository.PaymentHistoryRepository;
import dev.mvasylenko.paymentservice.repository.PaymentRepository;
import dev.mvasylenko.paymentservice.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static dev.mvasylenko.core.constants.CoreConstants.TAXI_MAX_ID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public PaymentDto processRentPayment(UUID driverId, BigDecimal carRentPrice) {
        Payment payment = new Payment(carRentPrice, driverId, TAXI_MAX_ID, LocalDateTime.now(), PaymentStatus.PENDING);
        paymentRepository.save(payment);
        return PaymentMapper.INSTANCE.paymentToPaymentDto(payment);
    }

    @Override
    public PaymentDto getPayment(UUID paymentId) {
        return PaymentMapper.INSTANCE.paymentToPaymentDto(getPaymentById(paymentId));
    }

    @Override
    public void changePaymentStatus(UUID paymentId, PaymentStatus status) {
        var payment = getPaymentById(paymentId);
        payment.setStatus(status);
        paymentRepository.save(payment);
    }

    private Payment getPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with id=" + paymentId + " wasn't found!"));
    }
}
