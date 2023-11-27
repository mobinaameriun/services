package com.example.Service_system.dto.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDto {
    @NotEmpty
    String cardNumber;
    @NotEmpty
    String cvv2;
    @NotEmpty
    String password;
}
