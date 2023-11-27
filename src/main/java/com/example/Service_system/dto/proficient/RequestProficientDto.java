package com.example.Service_system.dto.proficient;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestProficientDto {
    Long proficientId;
    String proficientEmailAddress;
    String proficientPassword;
}
