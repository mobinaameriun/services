package com.example.Service_system.dto.order;
import com.example.Service_system.entity.Client;
import com.example.Service_system.entity.SubService;
import com.example.Service_system.enumration.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseOrderDto {

    long id;
    int proposedPrice;
    String description;
    LocalDateTime timeForWork;
    String address;
    OrderStatus orderStatus;
    SubService subService;
    Client client;
}
