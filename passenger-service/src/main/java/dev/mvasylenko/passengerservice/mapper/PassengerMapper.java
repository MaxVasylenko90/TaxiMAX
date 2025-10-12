package dev.mvasylenko.passengerservice.mapper;

import dev.mvasylenko.core.dto.PassengerDto;
import dev.mvasylenko.passengerservice.entity.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PassengerMapper {
    PassengerMapper INSTANCE = Mappers.getMapper(PassengerMapper.class);

    Passenger passengerDtoToPassenger(PassengerDto dto);
    PassengerDto passengerToPassengerDto(Passenger passenger);
}
