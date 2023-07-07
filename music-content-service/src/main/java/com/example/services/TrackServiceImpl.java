package com.example.services;

import com.example.repositorys.AlbumRepository;
import com.example.repositorys.TracksRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@Slf4j
@RequiredArgsConstructor
public class TrackServiceImpl implements TracksService {
    private final AlbumRepository albumRepository;
    private final TracksRepository tracksRepository;

    public void uploadTracks(MultipartFile[] files) {

    }
}
