package com.example.Service_system.dto.order;

import com.example.Service_system.entity.Client;
import com.example.Service_system.entity.Proficient;
import com.example.Service_system.entity.SubService;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestOrderDto {
    long orderId;
    SubService subService;
    Client client;
    int proposedPrice;
    String description;
    String address;
    String timeForWork;
    Proficient proficient;
}
