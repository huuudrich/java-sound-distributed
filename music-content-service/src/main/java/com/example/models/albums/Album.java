package com.example.models.albums;

import com.example.models.tracks.Track;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "albums", schema = "public")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @JoinColumn(name ="path_to_png")
    String pathToPng;
    @OneToMany
    @JoinColumn(name = "track_id")
    List<Track> tracks;
}