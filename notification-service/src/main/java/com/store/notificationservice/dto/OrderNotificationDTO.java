package com.store.notificationservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderNotificationDTO {
    private String orderId;
    private String status;
    private String message;
}
