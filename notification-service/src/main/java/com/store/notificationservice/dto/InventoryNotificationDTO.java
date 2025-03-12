package com.store.notificationservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryNotificationDTO {
    private String productId;
    private int currentStock;
    private String message;
}
