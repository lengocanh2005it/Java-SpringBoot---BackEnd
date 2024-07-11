package com.backend.JavaBackend.dto.request;

import com.backend.JavaBackend.validator.BirthdayConstraints;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    @NotBlank(message = "FIRST_NAME_INVALID")
    String firstName;

    @NotBlank(message = "LAST_NAME_INVALID")
    String lastName;

    // custom validator annotation
    @BirthdayConstraints(min = 18, message = "BIRTHDAY_INVALID")
    LocalDate birthday;
}
