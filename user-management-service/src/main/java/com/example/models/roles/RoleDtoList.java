package com.example.models.roles;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleDtoList {
    String username;
    Collection<Role> roles;
}
