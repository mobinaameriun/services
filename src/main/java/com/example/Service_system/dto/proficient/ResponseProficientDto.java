package com.example.Service_system.dto.proficient;
import com.example.Service_system.enumration.ProficientStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseProficientDto {
    Long id;
    String firstName;
    String lastName;
    String emailAddress;
    ProficientStatus status;
    int score;
}
