package com.store.orderservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryRequestDTO {
    private String productId;
    private int requestedQuantity;
}
