package com.example.models.users;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewPasswordRequest {
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 20, message = "Password should be between 8 and 20 characters")
    String password;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 20, message = "Password should be between 8 and 20 characters")
    String confirmPassword;

    @AssertTrue(message = "Password and confirmed password do not match")
    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }
}
