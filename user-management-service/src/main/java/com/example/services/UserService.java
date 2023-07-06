package com.example.services;

import com.example.models.roles.Role;
import com.example.models.roles.RoleDtoList;
import com.example.models.users.UserAdminDto;
import com.example.models.users.UserDto;
import com.example.models.users.UserUpdateDto;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface UserService {
    UserAdminDto getUser(Long userId);

    UserDto updateUser(Long userId, UserUpdateDto userUpdateDto, HttpServletRequest request);

    void deleteUser(Long userId);

    List<UserAdminDto> getAllUsers(Pageable pageable);

    RoleDtoList getRoles(Long userId);

    UserAdminDto addUserRole(Long userId, Long roleId);

    List<Role> getAllRoles();

    void deleteUserRole(Long userId, Long roleId);

}
