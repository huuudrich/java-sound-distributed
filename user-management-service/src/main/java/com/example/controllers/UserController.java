package com.example.controllers;

import com.example.models.users.UserAdminDto;
import com.example.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("admin/users/")
public class UserController {
    private final UserService userService;

    @GetMapping("{userId}")
    public ResponseEntity<UserAdminDto> getUser(@PathVariable @Positive Long userId) {
        UserAdminDto userAdminDto = userService.getUser(userId);
        return ResponseEntity.ok(userAdminDto);
    }
}
