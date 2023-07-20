package com.example.models.tracks;

import com.example.models.albums.Album;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackDto {
    Long id;
    String title;
    String producer;
    Date uploadDate;
    Album album;
}
