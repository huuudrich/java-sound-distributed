package com.example.repositorys;

import com.example.models.tracks.Track;
import com.example.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TracksRepository extends JpaRepository<Track, Long> {
    List<Track> getAllByIdInAndProducerId(Collection<Long> trackIds, Long producerId);

    Optional<Track> findTrackByIdAndProducerId(Long trackId, Long producerId);

    List<Track> getTracksByProducer(User producer);
}
