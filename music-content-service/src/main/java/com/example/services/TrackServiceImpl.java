package com.example.services;

import com.example.mappers.TracksMapper;
import com.example.models.tracks.NewTrackNameDto;
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
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    public List<TrackDto> uploadTracks(Long userId, MultipartFile[] files) {
        List<Track> tracks = new ArrayList<>();
        User producer = findUserById(userId);

        for (MultipartFile file : files) {
            tracks.add(fileStorageService.storeFile(file, producer));
        }

        return TracksMapper.INSTANCE.listTrackToTrackDto(tracksRepository.saveAll(tracks));
    }

    @Transactional
    @Override
    public List<TrackDto> assignNames(List<NewTrackNameDto> newTrackNameDto, Long userId) {
        Map<Long, String> newTracks = newTrackNameDto
                .stream()
                .collect(Collectors.toMap(NewTrackNameDto::getTrackId, NewTrackNameDto::getNewTitle));
        List<Track> tracks = new ArrayList<>(tracksRepository.getAllByIdInAndProducerId(newTracks.keySet(), userId));
        log.info(String.format("Подтверждение названия треков пользователем с id: %s", userId));
        for (Track track : tracks) {
            track.setTitle(newTracks.get(track.getId()));
        }

        return TracksMapper.INSTANCE.listTrackToTrackDto(tracksRepository.saveAll(tracks));
    }
    @Transactional
    @Override
    public void removeTrack(Long trackId, Long userId) {
        Track track = tracksRepository.findTrackByIdAndProducerId(trackId, userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Track with id: %d not found and producerId: %d", trackId, userId)));
        tracksRepository.delete(track);
        fileStorageService.removeFile(track.getFileName());
    }

    @Override
    public List<TrackDto> getTracks(Long userId) {
        User producer = findUserById(userId);

        return TracksMapper.INSTANCE.listTrackToTrackDto(tracksRepository.getTracksByProducer(producer));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %d not found", userId)));
    }
}
