package com.store.productservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductNotificationDTO {
    private String productId;
    private String message;
}
