package com.example.controllers;

import com.example.models.tracks.NewTrackNameDto;
import com.example.models.tracks.TrackDto;
import com.example.services.TracksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PRODUCER')")
    @PostMapping("/upload/{userId}")
    public ResponseEntity<List<TrackDto>> uploadTracks(@RequestParam("file") MultipartFile[] files,
                                                       @PathVariable @Positive Long userId) {
        return new ResponseEntity<>(tracksService.uploadTracks(userId, files), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PRODUCER')")
    @PostMapping("/assign-names/{userId}")
    public ResponseEntity<List<TrackDto>> assignNames(@RequestBody List<NewTrackNameDto> trackInfos,
                                                      @PathVariable @Positive Long userId) {
        return new ResponseEntity<>(tracksService.assignNames(trackInfos, userId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PRODUCER')")
    @DeleteMapping("/{trackId}/remove/{userId}")
    public ResponseEntity<Void> removeTrack(@PathVariable @Positive Long trackId,
                                            @PathVariable @Positive Long userId) {
        tracksService.removeTrack(trackId, userId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_PRODUCER')")
    @PostMapping("/{userId}")
    public ResponseEntity<List<TrackDto>> getTracks(@PathVariable @Positive Long userId) {

        return new ResponseEntity<>(tracksService.getTracks(userId), HttpStatus.OK);
    }
}
