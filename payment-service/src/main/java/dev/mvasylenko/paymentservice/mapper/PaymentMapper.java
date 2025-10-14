package dev.mvasylenko.paymentservice.mapper;

import dev.mvasylenko.core.dto.PaymentDto;
import dev.mvasylenko.paymentservice.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    Payment paymentDtoToPayment(PaymentDto paymentDto);
    PaymentDto paymentToPaymentDto(Payment payment);
}
