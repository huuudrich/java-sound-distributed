package com.example.services;

import com.example.models.users.UserAdminDto;

public interface UserService {
    UserAdminDto getUser(Long userId);
}
