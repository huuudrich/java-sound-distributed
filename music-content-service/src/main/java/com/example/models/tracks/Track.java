package com.example.models.tracks;

import com.example.models.albums.Album;
import com.example.models.users.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tracks", schema = "public")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Track implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String title;
    @ManyToOne
    @JoinColumn(name = "producer_id")
    User producer;
    @JoinColumn(name = "upload_date")
    @Builder.Default
    Date uploadDate = new Date();
    @ManyToOne
    @JoinColumn(name = "album_id")
    Album album;
    Long duration;
    @JoinColumn(name = "file_path", unique = true)
    String filePath;
}
