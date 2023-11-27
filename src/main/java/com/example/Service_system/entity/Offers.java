package com.example.Service_system.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Offers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    Orders orders;
    @ManyToOne
    Proficient proficient;
    LocalDateTime dateOfRecord;
    int proposedPrice;
    LocalDateTime timeForStartWork;
    Time time;

    public Offers(Orders orders,Proficient proficient,
                  LocalDateTime dateOfRecord ,int proposedPrice,LocalDateTime timeForStartWork,Time time) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offers offers = (Offers) o;
        return id.equals(offers.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Offers{" +
                "id=" + id +
                '}';
    }
}
