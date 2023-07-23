package com.example.models.tracks;

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
    Long producerId;
    Date uploadDate;
}
