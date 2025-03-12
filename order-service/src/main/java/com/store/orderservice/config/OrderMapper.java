package com.store.orderservice.config;

import com.store.orderservice.dto.OrderRequestDTO;
import com.store.orderservice.dto.OrderResponseDTO;
import com.store.orderservice.dto.ProductResponseDTO;
import com.store.orderservice.entities.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequestDTO orderRequestDTO, ProductResponseDTO productResponseDTO) {
        BigDecimal totalPrice = productResponseDTO.getPrice()
                .multiply(BigDecimal.valueOf(orderRequestDTO.getQuantity()));
        return Order.builder()
                .productId(orderRequestDTO.getProductId())
                .quantity(orderRequestDTO.getQuantity())
                .totalPrice(totalPrice)
                .build();
    }


    public OrderResponseDTO toDto(Order order, ProductResponseDTO products) {
        return OrderResponseDTO.builder()
                .id(String.valueOf(order.getId()))
                .productResponseDTO(products)
                .build();
    }
}
