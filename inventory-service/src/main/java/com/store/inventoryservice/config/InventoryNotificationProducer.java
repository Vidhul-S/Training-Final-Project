package com.store.inventoryservice.config;

import com.store.inventoryservice.dto.InventoryNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryNotificationProducer {

    private final KafkaTemplate<String, InventoryNotificationDTO> kafkaTemplate;

    public void sendLowStockNotification(InventoryNotificationDTO dto) {
        kafkaTemplate.send("inventory-notif", dto);
        System.out.println("📢 Sent Low Stock Notification: " + dto);
    }

    public void sendInventoryUpdateNotification(InventoryNotificationDTO dto) {
        kafkaTemplate.send("inventory-notif", dto);
        System.out.println("✅ Sent Inventory Update Notification: " + dto);
    }
}
