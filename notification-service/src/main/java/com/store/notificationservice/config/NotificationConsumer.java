package com.store.notificationservice.config;

import com.store.notificationservice.dto.GatewayNotificationDTO;
import com.store.notificationservice.dto.InventoryNotificationDTO;
import com.store.notificationservice.dto.OrderNotificationDTO;
import com.store.notificationservice.dto.ProductNotificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationConsumer {

    @KafkaListener(topics = "product-notif", groupId = "notif-group")
    public void consumeProductNotification(ProductNotificationDTO dto) {
        log.info("📢 Product Notification: {}", dto.getMessage());
    }

    @KafkaListener(topics = "gateway-notif", groupId = "notif-group")
    public void consumeGatewayNotification(GatewayNotificationDTO dto) {
        log.info("📢 Gateway Notification: {}", dto.getMessage());
    }

    @KafkaListener(topics = "inventory-notif", groupId = "notif-group")
    public void consumeInventoryNotification(InventoryNotificationDTO dto) {
        log.info("📢 Inventory Notification: {}", dto.getMessage());
    }
    @KafkaListener(topics = "order-notif", groupId = "notif-group")
    public void consumeOrderNotification(OrderNotificationDTO dto) {
        log.info("📢 Order Notification: {}", dto.getMessage());
    }
}
