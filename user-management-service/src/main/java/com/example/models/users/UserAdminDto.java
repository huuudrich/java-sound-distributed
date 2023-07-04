package com.example.models.users;

import com.example.models.roles.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAdminDto {
    Long id;
    String username;
    String email;
    Collection<Role> roles;
}
