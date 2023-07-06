package com.example.mappers;

import com.example.models.jwt.JwtRequest;
import com.example.models.users.RegisterUserDto;
import com.example.models.users.User;
import com.example.models.users.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true)
    User registerUserDtoToUser(RegisterUserDto registerUserDto);

    UserDto userToUserDto(User user);

    JwtRequest userToJwtRequest(User user);
}
