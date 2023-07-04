package com.example.mappers;

import com.example.models.users.User;
import com.example.models.users.UserAdminDto;
import com.example.models.users.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    UserAdminDto userToUserAdminDto(User user);
}
