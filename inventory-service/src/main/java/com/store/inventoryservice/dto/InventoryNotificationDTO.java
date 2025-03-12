package com.store.inventoryservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryNotificationDTO {
    private String productId;
    private int stockQuantity;
    private int minimumStock;
    private String message;
}
