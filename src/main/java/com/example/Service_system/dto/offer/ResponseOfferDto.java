package com.example.Service_system.dto.offer;
import com.example.Service_system.entity.Orders;
import com.example.Service_system.entity.Proficient;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.sql.Time;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseOfferDto {
    Long id;
    Orders orders;
    Proficient proficient;
    LocalDateTime dateOfRecord;
    int proposedPrice;
    LocalDateTime timeForStartWork;
    Time time;
}
