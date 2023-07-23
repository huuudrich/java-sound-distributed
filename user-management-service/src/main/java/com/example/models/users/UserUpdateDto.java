package com.example.models.users;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateDto {
    @Size(min = 4, max = 100, message = "Username should be between 4 and 100 characters")
    @Pattern(regexp = ".+", message = "Username cannot be blank")
    String username;
    @Email(message = "Invalid email format")
    @Size(min = 2, max = 254, message = "Email should be between 2 and 254 characters")
    @Pattern(regexp = ".+", message = "Email cannot be blank")
    String email;
}
