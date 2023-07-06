package com.example.services;

import com.example.mappers.UserMapper;
import com.example.models.jwt.JwtRequest;
import com.example.models.jwt.JwtResponse;
import com.example.models.roles.Role;
import com.example.models.roles.RoleName;
import com.example.models.users.NewPasswordRequest;
import com.example.models.users.RegisterUserDto;
import com.example.models.users.User;
import com.example.models.users.UserDto;
import com.example.repositorys.RoleRepository;
import com.example.repositorys.UserRepository;
import com.example.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsService userDetailsService;
    private final KafkaTemplate<String, User> kafkaTemplate;

    @Transactional
    @Override
    public JwtResponse createAuthToken(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Неправильный логин или пароль");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        log.info(String.format("Create token with username: %s", authRequest.getUsername()));
        String token = jwtTokenUtils.generateToken(userDetails);
        return new JwtResponse(token);
    }

    @Transactional
    @Override
    public UserDto createNewUser(RegisterUserDto registerUserDto) {
        String username = registerUserDto.getUsername();
        String email = registerUserDto.getEmail();

        if (userRepository.existsByUsername(username)) {
            throw new BadCredentialsException(String.format("The username: %s is already in use", username));
        }
        if (userRepository.existsByEmail(email)) {
            throw new BadCredentialsException(String.format("The email: %s is already in use", email));
        }

        Role role = roleRepository.getByName(RoleName.ROLE_LISTENER);
        User user = UserMapper.INSTANCE.registerUserDtoToUser(registerUserDto);
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setRoles(List.of(role));
        user = userRepository.save(user);

        log.info(String.format("Create user with id: %d", user.getId()));
        kafkaTemplate.send("user_create", user);

        return UserMapper.INSTANCE.userToUserDto(user);
    }

    @Transactional
    @Override
    public void updatePassword(Long userId, NewPasswordRequest newPasswordRequest, HttpServletRequest request) {
        User user = findById(userId);

        String header = request.getHeader("Authorization");
        String jwt = header.substring(7);
        String username = jwtTokenUtils.getUsername(jwt);

        if (!username.equals(user.getUsername())) {
            throw new BadCredentialsException(String.format("User with name: %s is not own", user.getUsername()));
        }

        user.setPassword(passwordEncoder.encode(newPasswordRequest.getPassword()));
        user = userRepository.save(user);
        log.info(String.format("Update user password with id: %d", user.getId()));
        kafkaTemplate.send("update_user_password", user);
    }

    private User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %d not found", userId)));
    }
}
