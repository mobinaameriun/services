package com.example.Service_system.dto.manager;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestManagerForSignupDto {
    String firstName;
    String lastName;
    @NotEmpty
    @Email(message = "this email invalid")
    String emailAddress;
    @NotEmpty
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8}$",
            message = " password invalid ")
    String password;
}
