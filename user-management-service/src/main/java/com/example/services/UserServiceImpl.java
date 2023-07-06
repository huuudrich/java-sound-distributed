package com.example.services;

import com.example.mappers.UserMapper;
import com.example.models.roles.Role;
import com.example.models.roles.RoleDtoList;
import com.example.models.users.User;
import com.example.models.users.UserAdminDto;
import com.example.models.users.UserDto;
import com.example.models.users.UserUpdateDto;
import com.example.repositorys.RoleRepository;
import com.example.repositorys.UserRepository;
import com.example.utils.CopyNonNullProperties;
import com.example.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, User> kafkaTemplate;
    private final RoleRepository roleRepository;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public UserAdminDto getUser(Long userId) {
        User user = findById(userId);
        log.info(String.format("Getting user with id: %d", userId));
        return UserMapper.INSTANCE.userToUserAdminDto(user);
    }

    @Transactional
    @Override
    public UserDto updateUser(Long userId, UserUpdateDto userUpdateDto, HttpServletRequest request) {
        String username = userUpdateDto.getUsername();
        String email = userUpdateDto.getEmail();

        User user = findById(userId);

        String header = request.getHeader("Authorization");
        String jwt = header.substring(7);
        String usernameAuth = jwtTokenUtils.getUsername(jwt);

        if (!usernameAuth.equals(user.getUsername())) {
            throw new BadCredentialsException(String.format("User with name: %s is not own", user.getUsername()));
        }
        if (userRepository.existsByUsername(username) && !user.getUsername().equals(userUpdateDto.getUsername())) {
            throw new BadCredentialsException(String.format("The username: %s is already in use", username));
        }
        if (userRepository.existsByEmail(email) && !user.getEmail().equals(userUpdateDto.getEmail())) {
            throw new BadCredentialsException(String.format("The email: %s is already in use", email));
        }

        User userUpdate = UserMapper.INSTANCE.userUpdateDtoToUser(userUpdateDto);
        CopyNonNullProperties.copy(userUpdate, user);

        user = userRepository.save(user);

        kafkaTemplate.send("user_update", user);

        return UserMapper.INSTANCE.userToUserDto(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        User user = findById(userId);

        kafkaTemplate.send("user_delete", user);

        userRepository.delete(user);
    }

    @Override
    public List<UserAdminDto> getAllUsers(Pageable pageable) {
        List<User> users = new ArrayList<>(userRepository.findAll(pageable).getContent());
        return UserMapper.INSTANCE.listUserToUserAdminDto(users);
    }

    @Override
    public RoleDtoList getRoles(Long userId) {
        User user = findById(userId);
        return UserMapper.INSTANCE.userToRoleDtoList(user);
    }

    @Transactional
    @Override
    public UserAdminDto addUserRole(Long userId, Long roleId) {
        Role role = findRoleById(roleId);
        User user = findById(userId);

        user.getRoles().add(role);
        user = userRepository.save(user);

        kafkaTemplate.send("user_update_roles", user);

        return UserMapper.INSTANCE.userToUserAdminDto(user);
    }

    @Override
    public List<Role> getAllRoles() {
        return new ArrayList<>(roleRepository.findAll());
    }

    @Override
    public void deleteUserRole(Long userId, Long roleId) {
        Role role = findRoleById(roleId);
        User user = findById(userId);
        user.getRoles().remove(role);

        kafkaTemplate.send("user_delete_role", userRepository.save(user));
    }

    private User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %d not found", userId)));
    }

    private Role findRoleById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Role with id: %d not found", roleId)));
    }
}
