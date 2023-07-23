package com.example.models.tracks;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class TrackNameDto {
    @NotBlank(message = "Track name cannot be blank")
    @Size(min = 4, max = 25, message = "Track name should be between 4 and 25 characters")
    String trackName;
}
