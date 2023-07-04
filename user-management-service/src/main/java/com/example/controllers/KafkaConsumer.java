package com.example.controllers;

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
}
