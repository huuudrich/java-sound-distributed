package com.example.mappers;

import com.example.models.roles.RoleDtoList;
import com.example.models.users.User;
import com.example.models.users.UserAdminDto;
import com.example.models.users.UserDto;
import com.example.models.users.UserUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    UserAdminDto userToUserAdminDto(User user);

    List<UserAdminDto> listUserToUserAdminDto(List<User> users);

    RoleDtoList userToRoleDtoList(User user);

    User userUpdateDtoToUser(UserUpdateDto userUpdateDto);
}
