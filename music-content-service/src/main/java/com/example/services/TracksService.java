package com.example.services;

import org.springframework.web.multipart.MultipartFile;

public interface TracksService {
    void uploadTracks(Long userId, MultipartFile[] files);
}
