package com.store.orderservice.service;

import com.store.orderservice.config.InventoryServiceClient;
import com.store.orderservice.config.OrderMapper;
import com.store.orderservice.config.ProductServiceClient;
import com.store.orderservice.dto.InventoryResponseDTO;
import com.store.orderservice.dto.OrderNotificationDTO;
import com.store.orderservice.dto.OrderRequestDTO;
import com.store.orderservice.dto.OrderResponseDTO;
import com.store.orderservice.dto.ProductResponseDTO;
import com.store.orderservice.entities.Order;
import com.store.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductServiceClient productServiceClient;
    private final InventoryServiceClient inventoryServiceClient;
    private final OrderNotificationProducer notificationProducer;

    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        ProductResponseDTO productDetail = productServiceClient.getProduct(orderRequestDTO.getProductId());
        if (productDetail == null || !productDetail.isFound()) {
            OrderNotificationDTO failureNotif = OrderNotificationDTO.builder()
                    .message("❌ Order failed: The product does not exist.")
                    .build();
            notificationProducer.sendOrderNotification(failureNotif);
            return OrderResponseDTO.builder()
                    .productResponseDTO(productDetail)
                    .build();
        }

        Order order = orderMapper.toEntity(orderRequestDTO,productDetail);
        orderRepository.save(order);

        OrderNotificationDTO processingNotif = OrderNotificationDTO.builder()
                .orderId(String.valueOf(order.getId()))
                .message("🟡 Your order is being processed.")
                .build();
        notificationProducer.sendOrderNotification(processingNotif);

        InventoryResponseDTO inventoryRequest = InventoryResponseDTO.builder()
                .productId(productDetail.getId())
                .availableQuantity(1)
                .build();
        boolean inStock = inventoryServiceClient.isInStock(inventoryRequest);

        if (!inStock) {
            OrderNotificationDTO failureNotif = OrderNotificationDTO.builder()
                    .orderId(String.valueOf(order.getId()))
                    .message("❌ Your order could not be processed due to insufficient stock.")
                    .build();
            notificationProducer.sendOrderNotification(failureNotif);
            orderRepository.save(order);
            return orderMapper.toDto(order, productDetail);
        } else {
            OrderNotificationDTO successNotif = OrderNotificationDTO.builder()
                    .orderId(String.valueOf(order.getId()))
                    .message("✅ Your order has been successfully placed!")
                    .build();
            notificationProducer.sendOrderNotification(successNotif);
        }

        OrderNotificationDTO vendorNotif = OrderNotificationDTO.builder()
                .orderId(String.valueOf(order.getId()))
                .message("📦 New Order Placed! Order ID: " + order.getId())
                .build();
        notificationProducer.sendVendorNotification(vendorNotif);

        orderRepository.save(order);
        return orderMapper.toDto(order, productDetail);
    }

    public Optional<Order> getOrderById(String id) {

        return orderRepository.findById(id) ;
    }
    public List<ProductResponseDTO> getAllProducts() {
        return productServiceClient.getAllProducts();
    }
}
