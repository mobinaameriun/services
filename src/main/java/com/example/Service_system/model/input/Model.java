package com.example.Service_system.model.input;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Model {
    String firstName;
    String lastName;
    String emailAddress;
    String service;
    int score;
}
