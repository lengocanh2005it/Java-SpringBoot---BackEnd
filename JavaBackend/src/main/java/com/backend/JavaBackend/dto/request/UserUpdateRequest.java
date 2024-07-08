package com.backend.JavaBackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    @NotBlank(message = "FIRST_NAME_INVALID")
    String firstName;

    @NotBlank(message = "LAST_NAME_INVALID")
    String lastName;

    LocalDate birthday;
}
