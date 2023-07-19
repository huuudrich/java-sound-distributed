package com.example.mappers;

import com.example.models.tracks.Track;
import com.example.models.tracks.TrackDto;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public interface TracksMapper {
    TracksMapper INSTANCE = Mappers.getMapper(TracksMapper.class);
    @Mapping(target = "user.id", source = "participationRequestDto.event")
    TrackDto trackToTrackDto(Track track);
}
