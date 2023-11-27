package com.example.Service_system.dto.comment;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestCommentDto {
    @Size(min = 0 , max = 5 )
    int score;
    String comment;
}
