package com.example.Service_system.dto.subService;

import com.example.Service_system.entity.Services;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestSubServiceForSaveDto {
    String name;
    float basePrice;
    String description;
}
