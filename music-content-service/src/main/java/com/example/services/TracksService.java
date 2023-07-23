package com.example.services;

import com.example.models.tracks.NewTrackNameDto;
import com.example.models.tracks.TrackDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TracksService {
    List<TrackDto> uploadTracks(Long userId, MultipartFile[] files);

    List<TrackDto> assignNames(List<NewTrackNameDto> newTrackNameDto, Long userId);

    void removeTrack(Long trackId, Long userId);

    List<TrackDto> getTracks(Long userId);
}
