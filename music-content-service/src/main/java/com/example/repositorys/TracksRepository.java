package com.example.repositorys;

import com.example.models.tracks.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TracksRepository extends JpaRepository<Track, Long> {
    List<Track> getAllByIdInAndProducerId(Collection<Long> trackIds, Long producerId);
}
