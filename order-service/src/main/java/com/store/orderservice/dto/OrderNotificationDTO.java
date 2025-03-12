package com.store.orderservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderNotificationDTO {
    private String orderId;
    private String message;
}
