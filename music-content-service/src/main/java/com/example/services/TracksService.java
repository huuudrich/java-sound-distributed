package com.example.services;

import com.example.models.tracks.Track;
import com.example.models.tracks.TrackDto;
import com.example.models.tracks.TrackNameDto;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;

public interface TracksService {
    List<TrackDto> uploadTracks(Long userId, MultipartFile[] files) throws UnsupportedAudioFileException, IOException;
}
