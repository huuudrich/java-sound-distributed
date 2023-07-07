package com.example.repositorys;

import com.example.models.tracks.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TracksRepository extends JpaRepository<Track, Long> {
}
