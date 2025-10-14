package dev.mvasylenko.paymentservice.mapper;

import dev.mvasylenko.core.dto.PaymentHistoryDto;
import dev.mvasylenko.paymentservice.entity.PaymentHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentHistoryMapper {
    public static final PaymentHistoryMapper INSTANCE = Mappers.getMapper(PaymentHistoryMapper.class);

    PaymentHistory historyDtoToHistory(PaymentHistoryDto paymentHistoryDto);
    PaymentHistoryDto historyToHistoryDto(PaymentHistory paymentHistory);
}
