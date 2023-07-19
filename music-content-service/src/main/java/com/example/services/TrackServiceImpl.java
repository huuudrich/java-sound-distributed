package com.example.services;

import com.example.models.tracks.Track;
import com.example.models.users.User;
import com.example.repositorys.AlbumRepository;
import com.example.repositorys.TracksRepository;
import com.example.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class TrackServiceImpl implements TracksService {
    private final AlbumRepository albumRepository;
    private final TracksRepository tracksRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public void uploadTracks(Long userId, MultipartFile[] files) {
        List<Track> tracks = new ArrayList<>();
        User producer = findUserById(userId);

        for (MultipartFile file : files) {
            String fileName = fileStorageService.storeFile(file);

            Track track = Track.builder()
                    .title(fileName)
                    .producer(producer)
                    .duration(file.)
                    .build();
            tracks.add(track);
        }
        tracksRepository.saveAll(tracks);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %d not found", userId)));
    }
}
