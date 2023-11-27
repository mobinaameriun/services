package com.example.Service_system.dto.manager;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseManagerDto {
    long id;
    String firstName;
    String lastName;
    String emailAddress;
    String password;
}
