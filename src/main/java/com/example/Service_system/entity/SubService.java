package com.example.Service_system.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class SubService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    float basePrice;
    String description;
    @JsonIgnore
    @ManyToOne
    Services service;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "profitient_subService",joinColumns = @JoinColumn(name = "sub_service_id"),
            inverseJoinColumns = @JoinColumn(name = "proficient_id"))
    List<Proficient> proficientList=new ArrayList<>();

    public void addProficient(Proficient proficient) {
        proficientList.add(proficient) ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubService that = (SubService) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SubService{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", basePrice=" + basePrice +
                ", description='" + description + '\'' +
                ", service=" + service +
                '}';
    }
}