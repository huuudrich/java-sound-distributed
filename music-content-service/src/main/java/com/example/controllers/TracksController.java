package com.example.controllers;

import com.example.services.TracksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/tracks")
public class TracksController {
    private final TracksService tracksService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadTracks(@RequestParam("file") MultipartFile[] files,
                                          @PathVariable @Positive Long userId) {
        tracksService.uploadTracks(userId, files);
    }
}
