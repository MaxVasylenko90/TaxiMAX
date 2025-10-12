package dev.mvasylenko.driverservice.mapper;

import dev.mvasylenko.core.dto.DriverDto;
import dev.mvasylenko.driverservice.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverMapper {
    DriverMapper INSTANCE = Mappers.getMapper(DriverMapper.class);

    DriverDto driverToDto(Driver driver);
    Driver driverDtoToDriver(DriverDto driverDto);
}
