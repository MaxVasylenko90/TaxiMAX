package dev.mvasylenko.carservice.mapper;

import dev.mvasylenko.carservice.entity.CarRentalHistory;
import dev.mvasylenko.core.dto.CarRentalHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarRentalHistoryMapper {
    CarRentalHistoryMapper INSTANCE = Mappers.getMapper(CarRentalHistoryMapper.class);

    CarRentalHistory historyDtoToHistory(CarRentalHistoryDto historyDto);
    CarRentalHistoryDto historyToHistoryDto(CarRentalHistory history);
}
