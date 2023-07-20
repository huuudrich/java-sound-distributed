package com.example.services;

import com.example.config.FileStorageProperties;
import com.example.exceptions.FileStorageException;
import com.example.models.tracks.Track;
import com.example.models.users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {
    private final Path fileStorageLocation; // путь к папке хранения

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Не удалось создать директорию для загрузки файлов", ex);
        }
    }

    public void storeFile(MultipartFile file) {
        // Генерация уникального имени файла
        String fileName = StringUtils.cleanPath(UUID.randomUUID() + "_" + file.getOriginalFilename());
        try {
            // Копирование файла в папку хранения
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException("Не удалось сохранить файл " + fileName, ex);
        }
    }

    public Track createTrack(MultipartFile file, User producer) throws IOException, UnsupportedAudioFileException {
        if (Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            throw new FileStorageException("Название трека не может быть пустым");
        }
        // Определение продолжительности трека
        long duration = getDurationWithMp3Spi(file.getResource().getFile());

        return Track.builder()
                .fileName(file.getOriginalFilename().replace(".mp3", ""))
                .producer(producer)
                .title(getTrackName(file.getResource().getFile()))
                .duration(duration)
                .build();
    }

    public static long getDurationWithMp3Spi(File file) throws UnsupportedAudioFileException, IOException {
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
        if (fileFormat instanceof TAudioFileFormat) {
            Map<?, ?> properties = fileFormat.properties();
            String key = "duration";
            Long microseconds = (Long) properties.get(key);
            return (int) (microseconds / 1000);
        } else {
            throw new UnsupportedAudioFileException();
        }
    }

    public static String getTrackName(File file) throws UnsupportedAudioFileException, IOException {
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
        if (fileFormat instanceof TAudioFileFormat) {
            Map<?, ?> properties = fileFormat.properties();
            String key = "title";
            return (String) properties.get(key);
        } else {
            throw new UnsupportedAudioFileException();
        }
    }
}
