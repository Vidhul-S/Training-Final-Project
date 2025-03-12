package com.store.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {
    private String id;
    private ProductResponseDTO productResponseDTO;
}
