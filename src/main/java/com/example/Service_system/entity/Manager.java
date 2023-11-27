package com.example.Service_system.entity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@DiscriminatorValue("manager")
public class Manager extends Users{
    public Manager( String firstName, String lastName, String emailAddress, String password, LocalDateTime signupDateTime) {
        super( firstName, lastName, emailAddress, password, signupDateTime);
    }
}
