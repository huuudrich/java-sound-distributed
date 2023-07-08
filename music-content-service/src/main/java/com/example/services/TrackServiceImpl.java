package com.example.services;

import com.example.models.users.User;
import com.example.repositorys.AlbumRepository;
import com.example.repositorys.TracksRepository;
import com.example.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;


@Service
@Slf4j
@RequiredArgsConstructor
public class TrackServiceImpl implements TracksService {
    private final AlbumRepository albumRepository;
    private final TracksRepository tracksRepository;
    private final UserRepository userRepository;

    public void uploadTracks(Long userId, MultipartFile[] files) {
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %d not found", userId)));
    }
}
