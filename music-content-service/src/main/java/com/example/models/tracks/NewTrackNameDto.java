package com.example.models.tracks;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewTrackNameDto {
    Long trackId;
    @NotBlank(message = "Track name cannot be blank")
    @Size(min = 4, max = 100, message = "Track name should be between 4 and 100 characters")
    String newTitle;
}
