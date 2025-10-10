package dev.mvasylenko.carservice.mapper;

import dev.mvasylenko.carservice.entity.Car;
import dev.mvasylenko.core.dto.CarDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDto carToCarDto(Car car);
    Car carDtoToCar(CarDto carDto);
}
