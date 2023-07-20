package com.example.mappers;

import com.example.models.tracks.Track;
import com.example.models.tracks.TrackDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TracksMapper {
    TracksMapper INSTANCE = Mappers.getMapper(TracksMapper.class);

    @Mapping(target = "producer", source = "track.producer.username")
    TrackDto trackToTrackDto(Track track);

    List<TrackDto> listTrackToTrackDto(List<Track> tracks);
}
