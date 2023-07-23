package com.example.services;

import com.example.models.users.User;
import com.example.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final UserRepository userRepository;

    @KafkaListener(topics = "user_create", groupId = "group_id")
    public void createUser(User user) {
        userRepository.save(user);
    }

    @KafkaListener(topics = "update_user_password", groupId = "group_id")
    public void updateUserPassword(User user) {
        userRepository.save(user);
    }

    @KafkaListener(topics = "user_update", groupId = "group_id")
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @KafkaListener(topics = "user_delete", groupId = "group_id")
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @KafkaListener(topics = "user_update_roles", groupId = "group_id")
    public void updateUserRoles(User user) {
        userRepository.save(user);
    }

    @KafkaListener(topics = "user_delete_role", groupId = "group_id")
    public void deleteUserRole(User user) {
        userRepository.save(user);
    }
}
