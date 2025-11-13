package dev.mvasylenko.authservice.mapper;

import dev.mvasylenko.authservice.entity.AuthUser;
import dev.mvasylenko.core.dto.AuthUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthUserMapper {
    AuthUserMapper INSTANCE = Mappers.getMapper(AuthUserMapper.class);

    AuthUserDto authUserToAuthUserDto(AuthUser authUser);
    AuthUser authUserDtoToAuthUser(AuthUserDto authUserDto);
}
