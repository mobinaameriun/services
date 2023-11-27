package com.example.Service_system.entity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;


@AllArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("client")
public class Client extends Users{


}
