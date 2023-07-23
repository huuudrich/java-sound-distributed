package com.example.services;

import com.example.models.jwt.JwtRequest;
import com.example.models.jwt.JwtResponse;
import com.example.models.users.NewPasswordRequest;
import com.example.models.users.RegisterUserDto;
import com.example.models.users.UserDto;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    JwtResponse createAuthToken(JwtRequest authRequest);

    UserDto createNewUser(RegisterUserDto registerUserDto);

    void updatePassword(Long userId, NewPasswordRequest newPasswordRequest, HttpServletRequest request);
}
