package com.example.controllers;

import com.example.models.jwt.JwtRequest;
import com.example.models.users.RegisterUserDto;
import com.example.models.users.UserDto;
import com.example.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return ResponseEntity.ok(authService.createAuthToken(authRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> userRegister(@RequestBody @Valid RegisterUserDto registerUserDto) {
        UserDto userDto = authService.createNewUser(registerUserDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
}
