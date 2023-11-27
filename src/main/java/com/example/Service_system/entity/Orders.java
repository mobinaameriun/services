package com.example.Service_system.entity;
import com.example.Service_system.enumration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
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
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    int proposedPrice;
    String description;
    LocalDateTime timeForWork;
    String address;
    OrderStatus orderStatus;
    @ManyToOne
    SubService subService;
    @ManyToOne
    Client client;
    @ManyToOne
    Proficient proficient;
    @OneToMany(mappedBy = "orders")
    List<Offers> offers =new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return id == orders.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                '}';
    }
}
