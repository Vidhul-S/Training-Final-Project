package com.store.orderservice.entities;

import com.store.orderservice.dto.ProductResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productId;
    private BigDecimal totalPrice;
    private int quantity;
}
