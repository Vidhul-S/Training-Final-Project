package com.store.inventoryservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponseDTO {
    private String productId;
    private int requestedQuantity;
    private boolean inStock;
}
