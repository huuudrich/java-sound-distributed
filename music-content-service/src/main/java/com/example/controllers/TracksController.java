package com.example.controllers;

import com.example.models.tracks.TrackDto;
import com.example.services.TracksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/tracks")
public class TracksController {
    private final TracksService tracksService;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<List<TrackDto>> uploadTracks(@RequestParam("file") MultipartFile[] files,
                                                       @PathVariable @Positive Long userId) {
        return new ResponseEntity<>(tracksService.uploadTracks(userId, files), HttpStatus.OK);
    }

    @PostMapping("/assign-names/{userId}")
    public ResponseEntity<?> assignNames(@RequestBody List<TrackInfo> trackInfos) {
        for (TrackInfo trackInfo : trackInfos) {
            // Найти файл по trackInfo.fileId и обновить его имя на trackInfo.trackName
            Track track = trackService.getTrackByFileId(trackInfo.getFileId());
            track.setName(trackInfo.getTrackName());
            trackService.save(track);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
