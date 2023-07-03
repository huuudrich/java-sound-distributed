package com.example.services;

import com.example.mappers.UserMapper;
import com.example.models.jwt.JwtRequest;
import com.example.models.jwt.JwtResponse;
import com.example.models.roles.Role;
import com.example.models.roles.RoleName;
import com.example.models.users.RegisterUserDto;
import com.example.models.users.User;
import com.example.models.users.UserDto;
import com.example.repositorys.RoleRepository;
import com.example.repositorys.UserRepository;
import com.example.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    @Transactional
    @Override
    public JwtResponse createAuthToken(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Неправильный логин или пароль");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return new JwtResponse(token);
    }

    @Transactional
    public UserDto createNewUser(RegisterUserDto registerUserDto) {
        Role role = roleRepository.getByName(RoleName.ROLE_LISTENER);
        User user = UserMapper.INSTANCE.registerUserDtoToUser(registerUserDto);

        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.getRoles().add(role);

        return UserMapper.INSTANCE.userToUserDto(userRepository.save(user));
    }
}
