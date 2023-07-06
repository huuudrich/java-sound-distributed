package com.example.controllers;

import com.example.models.roles.RoleDtoList;
import com.example.models.users.UserAdminDto;
import com.example.models.users.UserDto;
import com.example.models.users.UserUpdateDto;
import com.example.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final static String PATH_ROLE = "/role";

    @PatchMapping("{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable @Positive Long userId,
                                              @RequestBody @Valid UserUpdateDto userUpdateDto,
                                              HttpServletRequest request) {
        UserDto newUserUpdate = userService.updateUser(userId, userUpdateDto, request);
        return ResponseEntity.ok(newUserUpdate);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("{userId}")
    public ResponseEntity<UserAdminDto> getUser(@PathVariable @Positive Long userId) {
        UserAdminDto userAdminDto = userService.getUser(userId);
        return ResponseEntity.ok(userAdminDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Positive Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserAdminDto>> getAllUsers(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0", required = false) Integer from,
                                                          @PositiveOrZero @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "id"));
        List<UserAdminDto> usersDto = userService.getAllUsers(pageable);
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("{userId}" + PATH_ROLE)
    public ResponseEntity<RoleDtoList> getRoles(@PathVariable @Positive Long userId) {
        RoleDtoList roleList = userService.getRoles(userId);
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{userId}" + PATH_ROLE + "/{roleId}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable @Positive Long userId,
                                               @PathVariable @Positive Long roleId) {
        userService.deleteUserRole(userId, roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("{userId}" + PATH_ROLE + "/{roleId}")
    public ResponseEntity<UserAdminDto> addUserRole(@PathVariable @Positive Long userId,
                                                    @PathVariable @Positive Long roleId) {
        UserAdminDto userNewRoles = userService.addUserRole(userId, roleId);
        return ResponseEntity.ok(userNewRoles);
    }
}
