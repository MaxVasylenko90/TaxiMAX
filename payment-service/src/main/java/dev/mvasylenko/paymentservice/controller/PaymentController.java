package dev.mvasylenko.paymentservice.controller;

import dev.mvasylenko.core.dto.PaymentDto;
import dev.mvasylenko.core.dto.PaymentHistoryDto;
import dev.mvasylenko.paymentservice.service.PaymentHistoryService;
import dev.mvasylenko.paymentservice.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentHistoryService paymentHistoryService;

    public PaymentController(PaymentService paymentService, PaymentHistoryService paymentHistoryService) {
        this.paymentService = paymentService;
        this.paymentHistoryService = paymentHistoryService;
    }

    @GetMapping("/{paymentId}")
    public PaymentDto getPayment(@PathVariable UUID paymentId) {
        return paymentService.getPayment(paymentId);
    }

    @GetMapping("/{paymentId}")
    public List<PaymentHistoryDto> getPaymentHistory(@PathVariable UUID paymentId) {
        return paymentHistoryService.getPaymentHistory(paymentId);
    }
}
