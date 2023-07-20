package com.example.services;

import com.example.mappers.TracksMapper;
import com.example.models.tracks.Track;
import com.example.models.tracks.TrackDto;
import com.example.models.users.User;
import com.example.repositorys.AlbumRepository;
import com.example.repositorys.TracksRepository;
import com.example.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.transaction.Transactional;
import java.io.IOException;
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

    @Transactional
    @Override
    public List<TrackDto> uploadTracks(Long userId, MultipartFile[] files) throws UnsupportedAudioFileException, IOException {
        List<Track> tracks = new ArrayList<>();
        User producer = findUserById(userId);

        for (MultipartFile file : files) {
            tracks.add(fileStorageService.createTrack(file, producer));
        }

        tracks = tracksRepository.saveAll(tracks);

        for (MultipartFile file : files) {
            fileStorageService.storeFile(file);
        }
        return TracksMapper.INSTANCE.listTrackToTrackDto(tracks);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %d not found", userId)));
    }
}
