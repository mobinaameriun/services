package com.example.Service_system.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestUpdateClientDto {
    long id;
    String firstName;
    String lastName;
    @NotEmpty
    @Email(message = "this email invalid")
    String emailAddress;
}
