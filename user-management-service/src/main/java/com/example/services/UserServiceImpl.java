package com.example.services;

import com.example.mappers.UserMapper;
import com.example.models.users.User;
import com.example.models.users.UserAdminDto;
import com.example.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, User> kafkaTemplate;

    @Override
    public UserAdminDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %d not found", userId)));
        log.info(String.format("Getting user with id: %d", userId));
        return UserMapper.INSTANCE.userToUserAdminDto(user);
    }
}
