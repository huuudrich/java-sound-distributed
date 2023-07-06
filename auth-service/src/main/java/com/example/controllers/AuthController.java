package com.example.controllers;

import com.example.models.jwt.JwtRequest;
import com.example.models.jwt.JwtResponse;
import com.example.models.users.NewPasswordRequest;
import com.example.models.users.RegisterUserDto;
import com.example.models.users.UserDto;
import com.example.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody JwtRequest authRequest) {
        return ResponseEntity.ok(authService.createAuthToken(authRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> userRegister(@RequestBody @Valid RegisterUserDto registerUserDto) {
        UserDto userDto = authService.createNewUser(registerUserDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{userId}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable @Positive Long userId,
                                               @RequestBody @Valid NewPasswordRequest passwordRequest,
                                               HttpServletRequest request) {
        authService.updatePassword(userId, passwordRequest, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
