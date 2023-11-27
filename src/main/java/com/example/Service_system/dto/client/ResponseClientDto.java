package com.example.Service_system.dto.client;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseClientDto {
    long id;
    String firstName;
    String lastName;
    String emailAddress;
    LocalDateTime signupDateTime;
    int Validity;
}
