package com.example.models.users;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterUserDto {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, max = 100, message = "Username should be between 4 and 100 characters")
    String username;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 20, message = "Password should be between 8 and 20 characters")
    String password;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 20, message = "Password should be between 8 and 20 characters")
    String confirmPassword;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Size(min = 2, max = 254, message = "Email should be between 2 and 254 characters")
    String email;

    @AssertTrue(message = "Password and confirmed password do not match")
    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }
}
